package codeasus.projects.bank.eco.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "bank_accounts")
data class BankAccountEntity(
    @PrimaryKey val id: String,
    val name: String,
    val number: String,
    var numberLastChunk: String = "",
    val type: String,
    val scheme: String,
    val balance: Double,
    val currency: String,
    val cvv: String,
    val expiryDate: LocalDateTime
)