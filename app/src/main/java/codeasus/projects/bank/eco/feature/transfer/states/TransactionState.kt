package codeasus.projects.bank.eco.feature.transfer.states

import codeasus.projects.bank.eco.domain.local.model.enums.Currency

data class TransactionState(
    val accountName: String = "",
    val accountNumber: String = "",
    var amount: Double = 0.0,
    val currency: Currency  = Currency.USD
)