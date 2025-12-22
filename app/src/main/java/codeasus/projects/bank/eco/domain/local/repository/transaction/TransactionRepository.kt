package codeasus.projects.bank.eco.domain.local.repository.transaction

import codeasus.projects.bank.eco.domain.local.model.customer.CustomerModel
import codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel

interface TransactionRepository {
    suspend fun saveTransaction(model: TransactionModel)
    suspend fun getTransactionById(id: String): TransactionModel?
    suspend fun getAllTransactions(accountId: String?): Map<TransactionModel, CustomerModel>
    suspend fun getTransactionsByType(accountId: String?, types: List<String>): Map<TransactionModel, CustomerModel>
    suspend fun getTransactionsByKeyword(accountId: String?, keyword: String): Map<TransactionModel, CustomerModel>
    suspend fun getTransactionsByKeywordAndType(accountId: String?, keyword: String, types: List<String>): Map<TransactionModel, CustomerModel>
    suspend fun deleteTransactions()
}