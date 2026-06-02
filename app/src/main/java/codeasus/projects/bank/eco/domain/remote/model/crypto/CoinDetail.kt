package codeasus.projects.bank.eco.domain.remote.model.crypto

data class CoinDetail(
    val id: String,
    val symbol: String,
    val name: String,
    val description: String,
    val currentPrice: Double,
    val marketCapRank: Int
)