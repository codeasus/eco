package codeasus.projects.bank.eco.domain.remote.model.payment

import codeasus.projects.bank.eco.domain.local.model.enums.Currency

data class PaymentModel(
    val fromBankAccountNumber: String,
    val toBankAccountNumber: String,
    val amount: Double,
    val currency: Currency,
    val isStandard: Boolean,
)
