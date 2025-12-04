package codeasus.projects.bank.eco.domain.local.model.user

import codeasus.projects.bank.eco.domain.local.model.enums.BankAccountType
import codeasus.projects.bank.eco.domain.local.model.enums.BankAccountScheme
import codeasus.projects.bank.eco.domain.local.model.enums.Currency
import java.time.LocalDateTime

data class UserBankAccountModel(
    val id: Long,
    val name: String,
    val number: String,
    val type: BankAccountType,
    val scheme: BankAccountScheme,
    val balance: Double,
    val currency: Currency,
    val cvv: String,
    val expiryDate: LocalDateTime
)