package codeasus.projects.bank.eco.feature.crypto.presentation

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import codeasus.projects.bank.eco.R
import codeasus.projects.bank.eco.core.navigation.BottomNavbarScreen
import codeasus.projects.bank.eco.core.navigation.NavigationManager
import codeasus.projects.bank.eco.core.navigation.ui.BaseTopNavbar
import codeasus.projects.bank.eco.core.navigation.ui.BottomNavbar
import codeasus.projects.bank.eco.core.ui.shared.view.TextButton
import codeasus.projects.bank.eco.core.ui.shared.view.base.BaseScaffold
import codeasus.projects.bank.eco.core.ui.shared.view.base.MainBaseScreen
import codeasus.projects.bank.eco.core.ui.shared.view.utils.DataSourceDefaults
import codeasus.projects.bank.eco.core.ui.shared.view.utils.formatCoinPrice
import codeasus.projects.bank.eco.core.ui.shared.view.utils.formatCoinPriceChange
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme
import codeasus.projects.bank.eco.domain.remote.model.crypto.CoinPersonalAttributionData
import codeasus.projects.bank.eco.domain.remote.model.crypto.PricePoint
import codeasus.projects.bank.eco.feature.card.presentation.utils.InstantActionButton
import codeasus.projects.bank.eco.feature.crypto.CoinDropDownList
import codeasus.projects.bank.eco.feature.crypto.CoinsStat
import codeasus.projects.bank.eco.feature.crypto.PriceChangeByIntervalChip
import codeasus.projects.bank.eco.feature.crypto.PriceChangeByIntervalChipVariant
import codeasus.projects.bank.eco.feature.crypto.presentation.states.CoinIntent
import codeasus.projects.bank.eco.feature.crypto.presentation.states.CoinState
import codeasus.projects.bank.eco.feature.utils.UiState

@Composable
fun CoinScreenRoot(navigationManager: NavigationManager) {
    MainBaseScreen<CoinViewModel>(navigationManager) { navigationManager, vm ->
        val state = vm.state.collectAsStateWithLifecycle()

        CoinScreen(
            navigationManager = navigationManager,
            state = state.value,
            onAction = vm::handleIntent
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinScreen(
    navigationManager: NavigationManager,
    state: CoinState,
    onAction: (CoinIntent) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val scrollState = rememberScrollState()

    BaseScaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            BaseTopNavbar(
                navigationManager = navigationManager,
                scrollBehavior = scrollBehavior,
                title = BottomNavbarScreen.Coin.title,
                user = state.user,
                color = darkerBankAccountContainerColor,
            )
        },
        bottomBar = {
            BottomNavbar(navigationManager)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(56.dp)
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .backgroundCoin()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 36.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.Start
            ) {
                CoinDropDownList(
                    coins = state.personalCoins,
                    selectedCoin = state.selectedMainCoin
                ) { coin ->
                    onAction(CoinIntent.SelectCoin(coin))
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            modifier = Modifier.weight(1F),
                            text = "Crypto Portfolio",
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                fontWeight = FontWeight.Bold
                            )
                        )

                        Row(
                            modifier = Modifier.weight(1F),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            PriceChangeByIntervalChipVariant(
                                priceChangeInPercentByInterval = PriceChangeInPercentByInterval.HOUR,
                                isSelected = state.priceChangeIntervalForMainCoins == PriceChangeInPercentByInterval.HOUR
                            ) { onAction(CoinIntent.GetMainCoinPriceByHour) }
                            PriceChangeByIntervalChipVariant(
                                priceChangeInPercentByInterval = PriceChangeInPercentByInterval.DAY,
                                isSelected = state.priceChangeIntervalForMainCoins == PriceChangeInPercentByInterval.DAY
                            ) { onAction(CoinIntent.GetMainCoinPriceBy24Hours) }
                            PriceChangeByIntervalChipVariant(
                                priceChangeInPercentByInterval = PriceChangeInPercentByInterval.WEEK,
                                isSelected = state.priceChangeIntervalForMainCoins == PriceChangeInPercentByInterval.WEEK
                            ) { onAction(CoinIntent.GetMainCoinPriceByWeek) }
                        }
                    }

                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxSize()) {
                        Column(
                            modifier = Modifier.weight(1F),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "Total Balance",
                                style = TextStyle(
                                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                                    fontWeight = FontWeight.Bold
                                )
                            )

                            when (val coinStat = state.coinsStat) {
                                is UiState.Success -> {
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        val formattedCoinPriceChange =
                                            formatCoinPriceChange(changeInPercentage = coinStat.data.totalPercentageChange)

                                        Text(
                                            text = formatCoinPrice(
                                                coinStat.data.totalBalance,
                                                "usd"
                                            ),
                                            style = TextStyle(
                                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                                fontWeight = FontWeight.Bold
                                            )
                                        )

                                        Text(
                                            text = buildAnnotatedString {
                                                withStyle(style = SpanStyle(color = formattedCoinPriceChange.color)) {
                                                    append(
                                                        formattedCoinPriceChange.formattedChange
                                                    )
                                                }
                                                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onSurfaceVariant)) {
                                                    append(
                                                        " Last hour"
                                                    )
                                                }
                                            },
                                            style = TextStyle(
                                                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                    }
                                }

                                else -> {}
                            }
                        }

                        Column(
                            modifier = Modifier.weight(1F),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            when (val selectedCoin = state.selectedMainCoin) {
                                is UiState.Success -> {
                                    Text(
                                        text = selectedCoin.data.symbol.uppercase(),
                                        style = TextStyle(
                                            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )

                                    CoinLineChart(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .fillMaxHeight(),
                                        data = selectedCoin.data.priceFluctuationHistory,
                                        timeframe = state.priceChangeIntervalForMainCoins.toChartTimeframe()
                                    )
                                }

                                else -> {

                                }
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    InstantActionButton(R.drawable.ic_plus_minus, "Buy/Sell", enabled = true) {
                    }
                    InstantActionButton(R.drawable.ic_wallet, "Send/Receive", enabled = true) {
                    }
                    InstantActionButton(R.drawable.ic_convert, "Convert", enabled = true) {
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 132.dp, max = 264.dp)
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4F))
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "My Coins")
                    Spacer(modifier = Modifier.weight(1.0f))
                    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                        PriceChangeByIntervalChip(
                            priceChangeInPercentByInterval = PriceChangeInPercentByInterval.HOUR,
                            isSelected = state.priceChangeIntervalForPersonalCoins == PriceChangeInPercentByInterval.HOUR
                        ) { onAction(CoinIntent.GetPersonalCoinPriceByHour) }
                        PriceChangeByIntervalChip(
                            priceChangeInPercentByInterval = PriceChangeInPercentByInterval.DAY,
                            isSelected = state.priceChangeIntervalForPersonalCoins == PriceChangeInPercentByInterval.DAY
                        ) { onAction(CoinIntent.GetPersonalCoinPriceBy24Hours) }
                        PriceChangeByIntervalChip(
                            priceChangeInPercentByInterval = PriceChangeInPercentByInterval.WEEK,
                            isSelected = state.priceChangeIntervalForPersonalCoins == PriceChangeInPercentByInterval.WEEK
                        ) { onAction(CoinIntent.GetPersonalCoinPriceByWeek) }
                    }
                    TextButton("View all") { }
                }
                when (val coinsState = state.personalCoins) {
                    is UiState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is UiState.Success -> {
                        PersonalCoinCardGrid(
                            coins = coinsState.data,
                            priceChangeInPercentByInterval = state.priceChangeIntervalForPersonalCoins
                        ) { }
                    }

                    else -> {}
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4F))
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Coins")
                    Spacer(modifier = Modifier.weight(1.0f))
                    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                        PriceChangeByIntervalChip(
                            priceChangeInPercentByInterval = PriceChangeInPercentByInterval.HOUR,
                            isSelected = state.priceChangeIntervalForCoins == PriceChangeInPercentByInterval.HOUR
                        ) { onAction(CoinIntent.GetCoinPriceByHour) }
                        PriceChangeByIntervalChip(
                            priceChangeInPercentByInterval = PriceChangeInPercentByInterval.DAY,
                            isSelected = state.priceChangeIntervalForCoins == PriceChangeInPercentByInterval.DAY
                        ) { onAction(CoinIntent.GetCoinPriceBy24Hours) }
                        PriceChangeByIntervalChip(
                            priceChangeInPercentByInterval = PriceChangeInPercentByInterval.WEEK,
                            isSelected = state.priceChangeIntervalForCoins == PriceChangeInPercentByInterval.WEEK
                        ) { onAction(CoinIntent.GetCoinPriceByWeek) }
                    }
                    TextButton("View all") { }
                }
                when (val coinsState = state.coins) {
                    is UiState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(320.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is UiState.Success -> {
                        Coins(coinsState.data, state.priceChangeIntervalForCoins) { }
                    }

                    else -> {}
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CoinScreenDarkPreview() {
    EcoTheme {
        val previewData = run {
            val now = System.currentTimeMillis()
            List(24) { i ->
                PricePoint(
                    timestamp = now - (23 - i) * 3_600_000L,
                    price = 45_000.0 + (Math.random() - 0.5) * 2_000.0
                )
            }
        }

        CoinScreen(
            navigationManager = NavigationManager(rememberNavController()),
            state = CoinState(
                coins = UiState.Success(DataSourceDefaults.getCoins()),
                personalCoins = UiState.Success(
                    DataSourceDefaults.getCoins()
                        .take(3)
                        .map { it.copy(personalData = CoinPersonalAttributionData(1.2, true)) }),
                selectedMainCoin = UiState.Success(
                    DataSourceDefaults.getCoins()[0].copy(
                        priceFluctuationHistory = previewData
                    )
                ),
                mainCardCoins = UiState.Success(DataSourceDefaults.getCoins()),
                coinsStat = UiState.Success(CoinsStat(122323.434, 2.3)),

                ),
            onAction = {}
        )
    }
}