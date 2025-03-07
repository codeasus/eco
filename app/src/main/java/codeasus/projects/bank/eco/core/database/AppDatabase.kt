package codeasus.projects.bank.eco.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import codeasus.projects.bank.eco.core.database.converters.LocalDateTimeConverters
import codeasus.projects.bank.eco.data.local.dao.CustomerDao
import codeasus.projects.bank.eco.data.local.dao.TransactionDao
import codeasus.projects.bank.eco.data.local.entity.CustomerEntity
import codeasus.projects.bank.eco.data.local.entity.TransactionEntity

@Database(
    entities = [CustomerEntity::class, TransactionEntity::class],
    version = 4,
    exportSchema = false
)
@TypeConverters(LocalDateTimeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun customerDao(): CustomerDao
    abstract fun transactionDao(): TransactionDao
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE transactions ADD COLUMN isMe INTEGER NOT NULL DEFAULT 0")
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE transactions_new (
                id TEXT PRIMARY KEY NOT NULL,
                internalAccountNumber TEXT NOT NULL,
                externalAccountNumber TEXT NOT NULL,
                amount REAL NOT NULL,
                currency TEXT NOT NULL,
                rate REAL NOT NULL,
                type TEXT NOT NULL,
                status TEXT NOT NULL,
                createdAt TEXT NOT NULL,
                updatedAt TEXT NOT NULL,
                isMe INTEGER NOT NULL
            )
        """
        )

        db.execSQL(
            """
            INSERT INTO transactions_new 
            SELECT id, fromAccountNumber, toAccountNumber, amount, currency, 
                   rate, type, status, createdAt, updatedAt, isMe 
            FROM transactions
        """
        )

        db.execSQL("DROP TABLE transactions")

        db.execSQL("ALTER TABLE transactions_new RENAME TO transactions")
    }
}

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                """
            CREATE TABLE transactions_new (
                id TEXT PRIMARY KEY NOT NULL,
                internalAccountNumber TEXT NOT NULL,
                externalAccountNumber TEXT NOT NULL,
                amount REAL NOT NULL,
                currency TEXT NOT NULL,
                rate REAL NOT NULL,
                type TEXT NOT NULL,
                status TEXT NOT NULL,
                createdAt TEXT NOT NULL,
                updatedAt TEXT NOT NULL
            )
        """
            )

            db.execSQL(
                """
            INSERT INTO transactions_new 
            SELECT id, internalAccountNumber, externalAccountNumber, 
                   amount, currency, rate, type, status, createdAt, updatedAt
            FROM transactions
        """
            )

            db.execSQL("DROP TABLE transactions")

            db.execSQL("ALTER TABLE transactions_new RENAME TO transactions")
    }
}