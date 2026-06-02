package codeasus.projects.bank.eco.feature.crypto.presentation.states

import codeasus.projects.bank.eco.domain.remote.model.crypto.Coin

sealed class CoinIntent {
    data class SelectCoin(val coin: Coin): CoinIntent()

    data object GetCoinPriceByHour: CoinIntent()
    data object GetCoinPriceBy24Hours: CoinIntent()
    data object GetCoinPriceByWeek: CoinIntent()

    data object GetMainCoinPriceByHour: CoinIntent()
    data object GetMainCoinPriceBy24Hours: CoinIntent()
    data object GetMainCoinPriceByWeek: CoinIntent()

    data object GetPersonalCoinPriceByHour: CoinIntent()
    data object GetPersonalCoinPriceBy24Hours: CoinIntent()
    data object GetPersonalCoinPriceByWeek: CoinIntent()
}