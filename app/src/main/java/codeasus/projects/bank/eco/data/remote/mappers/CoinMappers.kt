package codeasus.projects.bank.eco.data.remote.mappers

import codeasus.projects.bank.eco.data.remote.model.crypto.CoinDetailDto
import codeasus.projects.bank.eco.data.remote.model.crypto.CoinMarketDto
import codeasus.projects.bank.eco.data.remote.model.crypto.MarketChartDto
import codeasus.projects.bank.eco.domain.remote.model.crypto.Coin
import codeasus.projects.bank.eco.domain.remote.model.crypto.CoinDetail
import codeasus.projects.bank.eco.domain.remote.model.crypto.PricePoint
import kotlin.collections.List

fun CoinMarketDto.toDomain() = Coin(
    id = id,
    symbol = symbol,
    name = name,
    currentPrice = currentPrice ?: 0.0,
    priceChangePercentage1h = priceChangePercentage1h,
    priceChangePercentage24h = priceChangePercentage24h,
    priceChangePercentage7d = priceChangePercentage7d,
    imageUrl = imageUrl
)

fun CoinMarketDto.toDomain(priceFluctuationHistory: List<PricePoint>) = Coin(
    id = id,
    symbol = symbol,
    name = name,
    currentPrice = currentPrice ?: 0.0,
    priceChangePercentage1h = priceChangePercentage1h,
    priceChangePercentage24h = priceChangePercentage24h,
    priceChangePercentage7d = priceChangePercentage7d,
    priceFluctuationHistory = priceFluctuationHistory,
    imageUrl = imageUrl
)

fun CoinDetailDto.toDomain(currency: String = "usd") = CoinDetail(
    id = id,
    symbol = symbol,
    name = name,
    description = description["en"] ?: "",
    currentPrice = marketData.currentPrice[currency] ?: 0.0,
    marketCapRank = marketCapRank ?: 0
)

fun MarketChartDto.toDomain(): List<PricePoint> = prices.map {
    PricePoint(timestamp = it[0].toLong(), price = it[1])
}