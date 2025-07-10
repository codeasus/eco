package codeasus.projects.bank.eco.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import codeasus.projects.bank.eco.core.database.converters.LocalDateTimeConverters
import codeasus.projects.bank.eco.data.local.dao.CustomerDao
import codeasus.projects.bank.eco.data.local.dao.SystemMessageDao
import codeasus.projects.bank.eco.data.local.dao.TransactionDao
import codeasus.projects.bank.eco.data.local.entity.CustomerEntity
import codeasus.projects.bank.eco.data.local.entity.TransactionEntity
import codeasus.projects.bank.eco.data.local.entity.SystemMessageEntity

@Database(
    entities = [CustomerEntity::class, TransactionEntity::class, SystemMessageEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(LocalDateTimeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun customerDao(): CustomerDao
    abstract fun transactionDao(): TransactionDao
    abstract fun systemMessageDao(): SystemMessageDao
}