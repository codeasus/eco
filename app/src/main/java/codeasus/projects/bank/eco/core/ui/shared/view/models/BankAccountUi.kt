package codeasus.projects.bank.eco.core.ui.shared.view.models

import codeasus.projects.bank.eco.domain.local.model.enums.BankAccountScheme
import codeasus.projects.bank.eco.domain.local.model.enums.BankAccountType

data class BankAccountUi(
    val id: Long,
    val name: String,
    val number: String,
    val type: BankAccountType,
    val scheme: BankAccountScheme,
    val balance: Double,
    val cvv: String,
    val expiryDate: String
)
