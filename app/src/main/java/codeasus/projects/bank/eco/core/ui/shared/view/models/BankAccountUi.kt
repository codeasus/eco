package codeasus.projects.bank.eco.core.ui.shared.view.models

import codeasus.projects.bank.eco.domain.local.model.enums.BankAccountScheme
import codeasus.projects.bank.eco.domain.local.model.enums.BankAccountType
import codeasus.projects.bank.eco.domain.local.model.enums.Currency

data class BankAccountUi(
    val id: String,
    val name: String,
    val number: String,
    val type: BankAccountType,
    val scheme: BankAccountScheme,
    val balance: String,
    val currency: Currency,
    val cvv: String,
    val expiryDate: String
)