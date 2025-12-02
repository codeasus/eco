package codeasus.projects.bank.eco.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bank_accounts")
data class BankAccountEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val number: String,
    var numberLastChunk: String = "",
    val type: String,
    val scheme: String,
    val balance: Double,
    val cvv: String,
    val expiryDate: String
)