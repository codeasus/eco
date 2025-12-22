package codeasus.projects.bank.eco.domain.local.model.transaction

import codeasus.projects.bank.eco.domain.local.model.enums.Currency
import codeasus.projects.bank.eco.domain.local.model.enums.TransactionStatus
import codeasus.projects.bank.eco.domain.local.model.enums.TransactionType
import com.android.identity.util.UUID
import java.time.LocalDateTime

data class TransactionModel(
    val id: UUID = UUID.randomUUID(),
    val accountIdSelf: String,
    val accountNumberFrom: String,
    val accountNumberTo: String,
    val amount: Double,
    val currency: Currency = Currency.USD,
    val rate: Double,
    val type: TransactionType = TransactionType.TRANSFER,
    val status: TransactionStatus = TransactionStatus.PENDING,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
)