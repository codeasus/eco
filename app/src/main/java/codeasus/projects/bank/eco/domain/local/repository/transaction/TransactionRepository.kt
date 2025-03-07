package codeasus.projects.bank.eco.domain.local.repository.transaction

import codeasus.projects.bank.eco.domain.local.model.customer.CustomerModel
import codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel

interface TransactionRepository {
    suspend fun saveTransaction(model: TransactionModel)
    suspend fun getTransactionById(id: String): TransactionModel?
    suspend fun getAllTransactions(cardNumber: String?): Map<TransactionModel, CustomerModel>
    suspend fun getTransactionsByType(cardNumber: String?, types: List<String>): Map<TransactionModel, CustomerModel>
    suspend fun getTransactionsByKeyword(cardNumber: String?, keyword: String): Map<TransactionModel, CustomerModel>
    suspend fun getTransactionsByKeywordAndType(cardNumber: String?, keyword: String, types: List<String>): Map<TransactionModel, CustomerModel>
    suspend fun deleteTransactions()
}