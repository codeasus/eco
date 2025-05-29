package codeasus.projects.bank.eco.data.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class BinLookupResponseDto(
    @SerialName("Status") val status: String? = null,
    @SerialName("Scheme") val scheme: String? = null,
    @SerialName("Type") val type: String? = null,
    @SerialName("Issuer") val issuer: String? = null,
    @SerialName("CardTier") val cardTier: String? = null,
    @SerialName("Country") val country: CountryDto? = null,
    @SerialName("Luhn") val luhn: Boolean? = null
) : Parcelable
