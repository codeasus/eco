package codeasus.projects.bank.eco.data.remote.model.crypto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class MarketChartDto(
    val prices: List<List<Double>> // Array of [timestamp, price]
): Parcelable