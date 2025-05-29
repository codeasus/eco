package codeasus.projects.bank.eco.data.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class CountryDto(
    @SerialName("A2") val a2: String? = null,
    @SerialName("A3") val a3: String? = null,
    @SerialName("N3") val n3: String? = null,
    @SerialName("ISD") val isd: String? = null,
    @SerialName("Name") val name: String? = null,
    @SerialName("Cont") val cont: String? = null
) : Parcelable

