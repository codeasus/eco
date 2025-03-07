package codeasus.projects.bank.eco.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import codeasus.projects.bank.eco.data.local.entity.CustomerEntity
import codeasus.projects.bank.eco.data.local.entity.TransactionEntity

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity)

    @Query("SELECT * FROM transactions WHERE id = :id")
    suspend fun getTransactionById(id: String): TransactionEntity?

    @Query("SELECT * FROM transactions as t LEFT JOIN customers as c ON t.externalAccountNumber = c.bankAccountNumber")
    suspend fun getAllTransactions(): Map<TransactionEntity, CustomerEntity>

    @Query("SELECT * FROM transactions as t LEFT JOIN customers as c ON t.externalAccountNumber = c.bankAccountNumber WHERE t.internalAccountNumber = :cardNumber")
    suspend fun getAllTransactions(cardNumber: String): Map<TransactionEntity, CustomerEntity>

    @Query("SELECT * FROM transactions as t LEFT JOIN customers as c ON t.externalAccountNumber = c.bankAccountNumber WHERE t.type IN (:types)")
    suspend fun getTransactionsByType(types: List<String>): Map<TransactionEntity, CustomerEntity>

    @Query("SELECT * FROM transactions as t LEFT JOIN customers as c ON t.externalAccountNumber = c.bankAccountNumber WHERE  t.internalAccountNumber = :cardNumber AND t.type IN (:types)")
    suspend fun getTransactionsByType(cardNumber: String, types: List<String>): Map<TransactionEntity, CustomerEntity>

    @Query("SELECT * FROM transactions as t LEFT JOIN customers as c ON t.externalAccountNumber = c.bankAccountNumber WHERE c.name LIKE '%' || :keyword || '%'")
    suspend fun getTransactionsByKeyword(keyword: String): Map<TransactionEntity, CustomerEntity>

    @Query("SELECT * FROM transactions as t LEFT JOIN customers as c ON t.externalAccountNumber = c.bankAccountNumber WHERE t.internalAccountNumber = :cardNumber AND c.name LIKE '%' || :keyword || '%' ")
    suspend fun getTransactionsByKeyword(cardNumber: String, keyword: String): Map<TransactionEntity, CustomerEntity>

    @Query("SELECT * FROM transactions as t LEFT JOIN customers as c ON t.externalAccountNumber = c.bankAccountNumber WHERE t.type IN (:types) AND c.name LIKE '%' || :keyword || '%' ")
    suspend fun getTransactionsByKeywordAndType(keyword: String, types: List<String>): Map<TransactionEntity, CustomerEntity>

    @Query("SELECT * FROM transactions as t LEFT JOIN customers as c ON t.externalAccountNumber = c.bankAccountNumber WHERE t.internalAccountNumber = :cardNumber AND c.name LIKE '%' || :keyword || '%' AND t.type IN (:types)")
    suspend fun getTransactionsByKeywordAndType(cardNumber: String, keyword: String, types: List<String>): Map<TransactionEntity, CustomerEntity>

    @Query("DELETE FROM transactions")
    suspend fun deleteTransactions()
}