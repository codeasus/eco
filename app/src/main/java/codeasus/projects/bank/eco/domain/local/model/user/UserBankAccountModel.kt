package codeasus.projects.bank.eco.domain.local.model.user

import codeasus.projects.bank.eco.domain.local.model.enums.BankAccountType
import codeasus.projects.bank.eco.domain.local.model.enums.Scheme
import java.time.LocalDateTime

data class UserBankAccountModel(
    val id: String,
    val name: String,
    val number: String,
    val type: BankAccountType,
    val scheme: Scheme,
    val balance: Double,
    val cvv: String,
    val expiryDate: LocalDateTime
)
