package codeasus.projects.bank.eco.domain.remote.model.crypto

data class CoinPersonalAttributionData(val amount: Double = 0.0, val isMine: Boolean = false)

data class Coin(
    val id: String,
    val symbol: String,
    val name: String,
    val currentPrice: Double,
    val priceChangePercentage1h: Double? = 0.0,
    val priceChangePercentage24h: Double? = 0.0,
    val priceChangePercentage7d: Double? = 0.0,
    val priceFluctuationHistory: List<PricePoint> = emptyList(),
    val imageUrl: String,
    val personalData: CoinPersonalAttributionData = CoinPersonalAttributionData()
)