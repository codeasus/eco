package codeasus.projects.bank.eco.feature.crypto.presentation.states

import codeasus.projects.bank.eco.core.ui.shared.mappers.toUserUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.UserUi
import codeasus.projects.bank.eco.core.ui.shared.view.utils.DataSourceDefaults
import codeasus.projects.bank.eco.domain.remote.model.crypto.Coin
import codeasus.projects.bank.eco.feature.crypto.CoinsStat
import codeasus.projects.bank.eco.feature.crypto.presentation.PriceChangeInPercentByInterval
import codeasus.projects.bank.eco.feature.utils.UiState

data class CoinState(
    val user: UserUi = DataSourceDefaults.unknownUser.first.toUserUi(),
    val selectedMainCoin: UiState<Coin> = UiState.Idle,
    val coinsStat: UiState<CoinsStat> = UiState.Idle,
    val mainCardCoins: UiState<List<Coin>> = UiState.Idle,
    val personalCoins: UiState<List<Coin>> = UiState.Empty,
    val coins: UiState<List<Coin>> = UiState.Idle,
    val priceChangeIntervalForCoins: PriceChangeInPercentByInterval = PriceChangeInPercentByInterval.DAY,
    val priceChangeIntervalForMainCoins: PriceChangeInPercentByInterval = PriceChangeInPercentByInterval.DAY,
    val priceChangeIntervalForPersonalCoins: PriceChangeInPercentByInterval = PriceChangeInPercentByInterval.DAY
)
