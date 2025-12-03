package codeasus.projects.bank.eco.core.ui.shared.view.models

import androidx.annotation.DrawableRes

data class CustomerUi(
    val name: String,
    @param:DrawableRes val profileImg: Int,
    val bankAccount: CustomerBankAccountUi
)
