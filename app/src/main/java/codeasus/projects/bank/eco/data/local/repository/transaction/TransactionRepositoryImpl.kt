package codeasus.projects.bank.eco.data.local.repository.transaction

import codeasus.projects.bank.eco.data.local.dao.TransactionDao
import codeasus.projects.bank.eco.data.local.mappers.toCustomerModel
import codeasus.projects.bank.eco.data.local.mappers.toTransactionEntity
import codeasus.projects.bank.eco.data.local.mappers.toTransactionModel
import codeasus.projects.bank.eco.domain.local.model.customer.CustomerModel
import codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel
import codeasus.projects.bank.eco.domain.local.repository.transaction.TransactionRepository
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(private val transactionDao: TransactionDao) : TransactionRepository {

    override suspend fun saveTransaction(model: TransactionModel) {
        transactionDao.insertTransaction(model.toTransactionEntity())
    }

    override suspend fun getTransactionById(id: String): TransactionModel? {
        return transactionDao.getTransactionById(id)?.toTransactionModel()
    }

    override suspend fun getAllTransactions(accountId: String?): Map<TransactionModel, CustomerModel> {
        if (accountId != null) {
            return transactionDao.getTransactionsByAccountId(accountId).entries.associate { (transaction, customer) -> transaction.toTransactionModel() to customer.toCustomerModel() }
        }
        return transactionDao.getTransactions().entries.associate { (transaction, customer) -> transaction.toTransactionModel() to customer.toCustomerModel() }
    }

    override suspend fun getTransactionsByType(accountId: String?, types: List<String>): Map<TransactionModel, CustomerModel> {
        if (accountId != null) {
            return transactionDao.getTransactionsByType(accountId, types).entries.associate { (transaction, customer) -> transaction.toTransactionModel() to customer.toCustomerModel() }
        }
        return transactionDao.getTransactionsByType(types).entries.associate { (transaction, customer) -> transaction.toTransactionModel() to customer.toCustomerModel() }
    }

    override suspend fun getTransactionsByKeyword(accountId: String?, keyword: String): Map<TransactionModel, CustomerModel> {
        if (accountId != null) {
            return transactionDao.getTransactionsByKeyword(accountId, keyword).entries.associate { (transaction, customer) -> transaction.toTransactionModel() to customer.toCustomerModel() }
        }
        return transactionDao.getTransactionsByKeyword(keyword).entries.associate { (transaction, customer) ->
            transaction.toTransactionModel() to customer.toCustomerModel()
        }
    }

    override suspend fun getTransactionsByKeywordAndType(accountId: String?, keyword: String, types: List<String>): Map<TransactionModel, CustomerModel> {
        if(accountId != null) {
            return transactionDao.getTransactionsByKeywordAndType(accountId, keyword, types).entries.associate { (transaction, customer) -> transaction.toTransactionModel() to customer.toCustomerModel() }
        }
        return transactionDao.getTransactionsByKeywordAndType(keyword, types).entries.associate { (transaction, customer) ->
            transaction.toTransactionModel() to customer.toCustomerModel()
        }
    }

    override suspend fun deleteTransactions() {
        transactionDao.deleteTransactions()
    }
}