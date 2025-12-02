package codeasus.projects.bank.eco.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import codeasus.projects.bank.eco.data.local.entity.BankAccountEntity

@Dao
interface BankAccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBankAccount(account: BankAccountEntity)

    @Query("SELECT * FROM bank_accounts WHERE id = :id")
    suspend fun getBankAccountById(id: Long): BankAccountEntity?

    @Query("SELECT * FROM bank_accounts")
    suspend fun getAllBankAccounts(): List<BankAccountEntity>

    @Query("DELETE FROM bank_accounts WHERE id = :id")
    suspend fun deleteBankAccount(id: Long)

    @Query("DELETE FROM bank_accounts")
    suspend fun deleteBankAccounts()
}