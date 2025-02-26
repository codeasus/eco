package codeasus.projects.bank.eco.domain.local.repository.transaction

import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    suspend fun saveTransaction(transaction: codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel)
    suspend fun getTransactionById(id: String): codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel?
    fun getAllTransactions(): Flow<List<codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel>>
}