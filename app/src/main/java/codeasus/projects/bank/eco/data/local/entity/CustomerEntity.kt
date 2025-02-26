package codeasus.projects.bank.eco.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customers")
data class CustomerEntity(
    @PrimaryKey val name: String,
    val profileImgResourceId: Int,
    val bankAccountName: String,
    val bankAccountNumber: String
)