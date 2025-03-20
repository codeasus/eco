package codeasus.projects.bank.eco.domain.local.model.user

import codeasus.projects.bank.eco.domain.local.model.enums.BankAccountType
import java.time.LocalDateTime

data class UserBankAccountModel(
    val name: String,
    val number: String,
    val type: BankAccountType,
    val balance: Double,
    val ccv: String,
    val expiryDate: LocalDateTime
)
