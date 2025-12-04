package codeasus.projects.bank.eco.core.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import codeasus.projects.bank.eco.core.database.converters.LocalDateTimeConverters
import codeasus.projects.bank.eco.data.local.dao.BankAccountDao
import codeasus.projects.bank.eco.data.local.dao.CustomerDao
import codeasus.projects.bank.eco.data.local.dao.SystemMessageDao
import codeasus.projects.bank.eco.data.local.dao.TransactionDao
import codeasus.projects.bank.eco.data.local.entity.BankAccountEntity
import codeasus.projects.bank.eco.data.local.entity.CustomerEntity
import codeasus.projects.bank.eco.data.local.entity.TransactionEntity
import codeasus.projects.bank.eco.data.local.entity.SystemMessageEntity

@Database(
    entities = [
        CustomerEntity::class,
        TransactionEntity::class,
        SystemMessageEntity::class,
        BankAccountEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(LocalDateTimeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun customerDao(): CustomerDao
    abstract fun transactionDao(): TransactionDao
    abstract fun systemMessageDao(): SystemMessageDao
    abstract fun bankAccountDao(): BankAccountDao
}

//val MIGRATION_1_2 = object : Migration(1, 2) {
//    override fun migrate(db: SupportSQLiteDatabase) {
//        db.execSQL(
//            "ALTER TABLE bank_accounts ADD COLUMN currency TEXT NOT NULL DEFAULT 'EUR'"
//        )
//    }
//}