package codeasus.projects.bank.eco.domain.local.model.user

import java.time.LocalDateTime

data class UserBankAccountModel(
    val name: String,
    val number: String,
    val balance: Double,
    val ccv: String,
    val expiryDate: LocalDateTime
)
