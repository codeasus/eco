package codeasus.projects.bank.eco.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey val id: String,
    val internalAccountNumber: String,
    val externalAccountNumber: String,
    val amount: Double,
    val currency: String,
    val rate: Double,
    val type: String,
    val status: String,
    val createdAt: String,
    val updatedAt: String
)