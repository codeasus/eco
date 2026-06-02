package codeasus.projects.bank.eco.data.remote.model.crypto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class SearchResponseDto(val coins: List<SearchCoinDto>): Parcelable