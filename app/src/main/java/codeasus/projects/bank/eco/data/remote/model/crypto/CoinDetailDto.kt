package codeasus.projects.bank.eco.data.remote.model.crypto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class CoinDetailDto(
    val id: String,
    val symbol: String,
    val name: String,
    val description: Map<String, String>,
    @SerialName("market_data") val marketData: MarketDataDto,
    @SerialName("market_cap_rank") val marketCapRank: Int? = 0
): Parcelable