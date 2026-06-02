package codeasus.projects.bank.eco.data.remote.model.crypto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class CoinMarketDto(
    val id: String,
    val symbol: String,
    val name: String,
    @SerialName("current_price") val currentPrice: Double? = 0.0,
    @SerialName("price_change_percentage_1h_in_currency") val priceChangePercentage1h: Double? = 0.0,
    @SerialName("price_change_percentage_24h") val priceChangePercentage24h: Double? = 0.0,
    @SerialName("price_change_percentage_7d_in_currency") val priceChangePercentage7d: Double? = 0.0,
    @SerialName("image") val imageUrl: String
): Parcelable