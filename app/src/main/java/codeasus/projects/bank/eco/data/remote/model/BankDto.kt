package codeasus.projects.bank.eco.data.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BankDto(
    val name: String?
) : Parcelable
