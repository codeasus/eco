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

    @Query("SELECT * FROM transactions as t LEFT JOIN customers as c ON t.accountNumberTo = c.bankAccountNumber ORDER BY t.updatedAt ASC")
    suspend fun getTransactions(): Map<TransactionEntity, CustomerEntity>

    @Query("SELECT * FROM transactions as t LEFT JOIN customers as c ON t.accountNumberTo = c.bankAccountNumber WHERE t.accountIdSelf = :accountId ORDER BY t.updatedAt ASC")
    suspend fun getTransactionsByAccountId(accountId: String): Map<TransactionEntity, CustomerEntity>

    @Query("SELECT * FROM transactions as t LEFT JOIN customers as c ON t.accountNumberTo = c.bankAccountNumber WHERE t.type IN (:types) ORDER BY t.updatedAt ASC")
    suspend fun getTransactionsByType(types: List<String>): Map<TransactionEntity, CustomerEntity>

    @Query("SELECT * FROM transactions as t LEFT JOIN customers as c ON t.accountNumberTo = c.bankAccountNumber WHERE  t.accountIdSelf = :accountId AND t.type IN (:types) ORDER BY t.updatedAt ASC")
    suspend fun getTransactionsByType(accountId: String, types: List<String>): Map<TransactionEntity, CustomerEntity>

    @Query("SELECT * FROM transactions as t LEFT JOIN customers as c ON t.accountNumberTo = c.bankAccountNumber WHERE c.name LIKE '%' || :keyword || '%' ORDER BY t.updatedAt ASC")
    suspend fun getTransactionsByKeyword(keyword: String): Map<TransactionEntity, CustomerEntity>

    @Query("SELECT * FROM transactions as t LEFT JOIN customers as c ON t.accountNumberTo = c.bankAccountNumber WHERE t.accountIdSelf = :accountId AND c.name LIKE '%' || :keyword || '%' ORDER BY t.updatedAt ASC")
    suspend fun getTransactionsByKeyword(accountId: String, keyword: String): Map<TransactionEntity, CustomerEntity>

    @Query("SELECT * FROM transactions as t LEFT JOIN customers as c ON t.accountNumberTo = c.bankAccountNumber WHERE t.type IN (:types) AND c.name LIKE '%' || :keyword || '%' ORDER BY t.updatedAt ASC")
    suspend fun getTransactionsByKeywordAndType(keyword: String, types: List<String>): Map<TransactionEntity, CustomerEntity>

    @Query("SELECT * FROM transactions as t LEFT JOIN customers as c ON t.accountNumberTo = c.bankAccountNumber WHERE t.accountIdSelf = :accountId AND c.name LIKE '%' || :keyword || '%' AND t.type IN (:types) ORDER BY t.updatedAt ASC")
    suspend fun getTransactionsByKeywordAndType(accountId: String, keyword: String, types: List<String>): Map<TransactionEntity, CustomerEntity>

    @Query("DELETE FROM transactions")
    suspend fun deleteTransactions()
}