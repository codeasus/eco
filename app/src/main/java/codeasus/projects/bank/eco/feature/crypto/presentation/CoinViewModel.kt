package codeasus.projects.bank.eco.feature.crypto.presentation

import androidx.lifecycle.viewModelScope
import codeasus.projects.bank.eco.core.ui.shared.viewmodel.base.BaseViewModel
import codeasus.projects.bank.eco.domain.local.repository.user.UserRepository
import codeasus.projects.bank.eco.domain.remote.model.crypto.ChartTimeframe
import codeasus.projects.bank.eco.domain.remote.model.crypto.Coin
import codeasus.projects.bank.eco.domain.remote.model.crypto.CoinPersonalAttributionData
import codeasus.projects.bank.eco.domain.remote.model.crypto.PricePoint
import codeasus.projects.bank.eco.domain.remote.repository.crypto.CoinRepository
import codeasus.projects.bank.eco.domain.utils.DomainResult
import codeasus.projects.bank.eco.feature.crypto.CoinsStat
import codeasus.projects.bank.eco.feature.crypto.presentation.states.CoinIntent
import codeasus.projects.bank.eco.feature.crypto.presentation.states.CoinState
import codeasus.projects.bank.eco.feature.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.map
import kotlin.collections.reduce
import kotlin.collections.sumOf

@HiltViewModel
class CoinViewModel @Inject constructor(userRepository: UserRepository, private val coinRepository: CoinRepository) : BaseViewModel(userRepository) {
    private val _state = MutableStateFlow(CoinState())
    val state = _state.asStateFlow()

    init {
        loadUser()
        loadAllCoins()
        loadMyCoins()
        loadMyCoinsStats()
        observePriceTimeIntervalChanges()
        observePriceTimeIntervalChangesForMainCoins()
        observePriceTimeIntervalChangesForPersonalCoins()
    }

    fun handleIntent(intent: CoinIntent) {
        when (intent) {
            is CoinIntent.SelectCoin -> {
                selectCoin(intent.coin)
            }

            is CoinIntent.GetCoinPriceByHour -> {
                getCoinPriceChangeByHour()
            }

            is CoinIntent.GetCoinPriceBy24Hours -> {
                getCoinPriceChangeBy24Hours()
            }

            is CoinIntent.GetCoinPriceByWeek -> {
                getCoinPriceChangeByWeek()
            }

            is CoinIntent.GetMainCoinPriceByHour -> {
                getMainCoinPriceChangeByHour()
            }

            is CoinIntent.GetMainCoinPriceBy24Hours -> {
                getMainCoinPriceChangeBy24Hours()
            }

            is CoinIntent.GetMainCoinPriceByWeek -> {
                getMainCoinPriceChangeByWeek()
            }

            is CoinIntent.GetPersonalCoinPriceByHour -> {
                getPersonalCoinPriceChangeByHour()
            }

            is CoinIntent.GetPersonalCoinPriceBy24Hours -> {
                getPersonalCoinPriceChangeBy24Hours()
            }

            is CoinIntent.GetPersonalCoinPriceByWeek -> {
                getPersonalCoinPriceChangeByWeek()
            }
        }
    }

    private fun loadUser() {
        viewModelScope.launch {
            user.collect { user ->
                if (user != null) {
                    _state.update { it.copy(user = user) }
                }
            }
        }
    }

    private suspend fun fetchChart(id: String, timeframe: ChartTimeframe): DomainResult<List<PricePoint>> {
        val currentTimeSeconds = System.currentTimeMillis() / 1000

        val fromTimeSeconds = when (timeframe) {
            ChartTimeframe.HOUR -> currentTimeSeconds - 3600
            ChartTimeframe.DAY -> currentTimeSeconds - 86400
            ChartTimeframe.WEEK -> currentTimeSeconds - 604800
        }

        return coinRepository.getCoinHistory(id = id, fromTimestampSeconds = fromTimeSeconds, toTimestampSeconds = currentTimeSeconds)
    }

    private fun loadAllCoins() {
        viewModelScope.launch {
            _state.update { it.copy(coins = UiState.Loading) }
            when (val result = coinRepository.getCoinsList(page = 1, coinsPerPage = 10)) {
                is DomainResult.Success -> {
                    _state.update { it.copy(coins = UiState.Success(result.data)) }
                }

                is DomainResult.Error -> {}
            }
        }
    }

    private fun loadMyCoins() {
        viewModelScope.launch {
            _state.update { it.copy(personalCoins = UiState.Loading) }

            val myCoinAmounts = coinRepository.getMyCoinAmount()

            val currentTimeSeconds = System.currentTimeMillis() / 1000

            val fromTimeSeconds = currentTimeSeconds - 3600

            when (val result = coinRepository.getMyCoinList(myCoinAmounts.keys.toList(), fromTimestampSeconds = fromTimeSeconds, toTimestampSeconds = currentTimeSeconds)) {
                is DomainResult.Success -> {
                    val updatedData = result.data.map { coin ->
                        val amount = myCoinAmounts[coin.id]
                        if (amount != null) {
                            coin.copy(personalData = CoinPersonalAttributionData(amount = amount, isMine = true))
                        } else {
                            coin
                        }
                    }

                    _state.update { it.copy(personalCoins = UiState.Success(updatedData)) }

                    if (state.value.mainCardCoins is UiState.Idle) {
                        if (updatedData.isNotEmpty()) {
                            val firstCoin = updatedData.first()
                            _state.update {
                                it.copy(mainCardCoins = UiState.Success(updatedData), selectedMainCoin = UiState.Success(firstCoin))
                            }
                        } else {
                            _state.update {
                                it.copy(mainCardCoins = UiState.Empty, selectedMainCoin = UiState.Empty)
                            }
                        }
                    }
                }

                is DomainResult.Error -> {}
            }
        }
    }

    private fun loadMyCoinsStats() {
        viewModelScope.launch {
            _state.update { it.copy(coinsStat = UiState.Loading) }
            _state.collectLatest { state ->
                when (val personalCoinsState = state.personalCoins) {
                    is UiState.Success -> {
                        val personalCoins = personalCoinsState.data
                        val totalBalance = personalCoins.map { it.personalData.amount * it.currentPrice }.reduce { acc, d -> acc + d }
                        val totalPercentageChange = personalCoins.map { it.priceChangePercentage24h }.sumOf { it ?: 0.0 }
                        _state.update { it.copy(coinsStat = UiState.Success(CoinsStat(totalBalance, totalPercentageChange))) }
                    }

                    else -> {}
                }
            }
        }
    }

    private fun selectCoin(coin: Coin) {
        viewModelScope.launch {
            val chartResult = fetchChart(id = coin.id, timeframe = ChartTimeframe.DAY)
            val chartData = if (chartResult is DomainResult.Success) chartResult.data else emptyList()

            _state.update {
                it.copy(
                    selectedMainCoin = UiState.Success(coin.copy(priceFluctuationHistory = chartData)),
                )
            }
        }
    }

    private fun observePriceTimeIntervalChanges() {
        viewModelScope.launch {
            _state
                .map { it.priceChangeIntervalForCoins }
                .distinctUntilChanged()
                .collectLatest { loadAllCoins() }
        }
    }

    private fun observePriceTimeIntervalChangesForMainCoins() {
        viewModelScope.launch {
            _state
                .map { it.priceChangeIntervalForMainCoins }
                .distinctUntilChanged()
                .collectLatest {
                    when (val selectedCoinState = _state.value.selectedMainCoin) {
                        is UiState.Success -> {
                            val selectedCoin = selectedCoinState.data

                            val chartResult = fetchChart(selectedCoin.id, it.toChartTimeframe())

                            val chartData = if (chartResult is DomainResult.Success) chartResult.data else emptyList()

                            _state.update { state -> state.copy(selectedMainCoin = UiState.Success(selectedCoin.copy(priceFluctuationHistory = chartData))) }
                        }
                        else -> {}
                    }
                }
        }
    }

    private fun observePriceTimeIntervalChangesForPersonalCoins() {
        viewModelScope.launch {
            _state
                .map { it.priceChangeIntervalForPersonalCoins }
                .distinctUntilChanged()
                .collectLatest { loadMyCoins() }
        }
    }

    private fun getCoinPriceChangeByHour() {
        _state.update { it.copy(priceChangeIntervalForCoins = PriceChangeInPercentByInterval.HOUR) }
    }

    private fun getCoinPriceChangeBy24Hours() {
        _state.update { it.copy(priceChangeIntervalForCoins = PriceChangeInPercentByInterval.DAY) }
    }

    private fun getCoinPriceChangeByWeek() {
        _state.update { it.copy(priceChangeIntervalForCoins = PriceChangeInPercentByInterval.WEEK) }
    }

    private fun getMainCoinPriceChangeByHour() {
        _state.update { it.copy(priceChangeIntervalForMainCoins = PriceChangeInPercentByInterval.HOUR) }
    }

    private fun getMainCoinPriceChangeBy24Hours() {
        _state.update { it.copy(priceChangeIntervalForMainCoins = PriceChangeInPercentByInterval.DAY) }
    }

    private fun getMainCoinPriceChangeByWeek() {
        _state.update { it.copy(priceChangeIntervalForMainCoins = PriceChangeInPercentByInterval.WEEK) }
    }

    private fun getPersonalCoinPriceChangeByHour() {
        _state.update { it.copy(priceChangeIntervalForPersonalCoins = PriceChangeInPercentByInterval.HOUR) }
    }

    private fun getPersonalCoinPriceChangeBy24Hours() {
        _state.update { it.copy(priceChangeIntervalForPersonalCoins = PriceChangeInPercentByInterval.DAY) }
    }

    private fun getPersonalCoinPriceChangeByWeek() {
        _state.update { it.copy(priceChangeIntervalForPersonalCoins = PriceChangeInPercentByInterval.WEEK) }
    }
}