package codeasus.projects.bank.eco.domain.local.model.transaction

import codeasus.projects.bank.eco.domain.local.model.enums.Currency
import java.time.LocalDateTime
import java.util.UUID

data class TransactionModel(
    val id: UUID = UUID.randomUUID(),
    val fromAccountNumber: String,
    val toAccountNumber: String,
    val amount: Double,
    val currency: Currency = Currency.USD,
    val rate: Double,
    val type: codeasus.projects.bank.eco.domain.local.model.enums.TransactionType = codeasus.projects.bank.eco.domain.local.model.enums.TransactionType.TRANSFER,
    val status: codeasus.projects.bank.eco.domain.local.model.enums.TransactionStatus = codeasus.projects.bank.eco.domain.local.model.enums.TransactionStatus.PENDING,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
)