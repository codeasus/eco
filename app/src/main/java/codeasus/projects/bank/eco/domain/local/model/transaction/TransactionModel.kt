package codeasus.projects.bank.eco.domain.local.model.transaction

import codeasus.projects.bank.eco.domain.local.model.enums.Currency
import codeasus.projects.bank.eco.domain.local.model.enums.TransactionStatus
import codeasus.projects.bank.eco.domain.local.model.enums.TransactionType
import java.time.LocalDateTime
import java.util.UUID

data class TransactionModel(
    val id: UUID = UUID.randomUUID(),
    val externalAccountNumber: String,
    val internalAccountNumber: String,
    val amount: Double,
    val currency: Currency = Currency.USD,
    val rate: Double,
    val type: TransactionType = TransactionType.TRANSFER,
    val status: TransactionStatus = TransactionStatus.PENDING,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
)