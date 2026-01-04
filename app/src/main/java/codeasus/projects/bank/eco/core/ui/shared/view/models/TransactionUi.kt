package codeasus.projects.bank.eco.core.ui.shared.view.models

import codeasus.projects.bank.eco.domain.local.model.enums.Currency
import codeasus.projects.bank.eco.domain.local.model.enums.TransactionStatus
import codeasus.projects.bank.eco.domain.local.model.enums.TransactionType

data class TransactionUi (
    val id: String,
    val accountIdSelf: String,
    val accountNumberFrom: String,
    val accountNumberTo: String,
    val amount: String,
    val currency: Currency = Currency.USD,
    val rate: String,
    val type: TransactionType = TransactionType.TRANSFER,
    val status: TransactionStatus = TransactionStatus.PENDING,
    val createdAt: String,
    val updatedAt: String
)