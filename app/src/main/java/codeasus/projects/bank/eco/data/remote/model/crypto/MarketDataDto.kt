package codeasus.projects.bank.eco.data.remote.model.crypto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class MarketDataDto(
    @SerialName("current_price") val currentPrice: Map<String, Double>
): Parcelable