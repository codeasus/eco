package codeasus.projects.bank.eco.data.local.repository.transaction

import codeasus.projects.bank.eco.data.local.dao.TransactionDao
import codeasus.projects.bank.eco.data.local.mappers.toCustomerModel
import codeasus.projects.bank.eco.data.local.mappers.toTransactionEntity
import codeasus.projects.bank.eco.data.local.mappers.toTransactionModel
import codeasus.projects.bank.eco.domain.local.model.customer.CustomerModel
import codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel
import codeasus.projects.bank.eco.domain.local.repository.transaction.TransactionRepository
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao
) : TransactionRepository {

    override suspend fun saveTransaction(model: TransactionModel) {
        transactionDao.insertTransaction(model.toTransactionEntity())
    }

    override suspend fun getTransactionById(id: String): TransactionModel? {
        return transactionDao.getTransactionById(id)?.toTransactionModel()
    }

    override suspend fun getAllTransactions(cardNumber: String?): Map<TransactionModel, CustomerModel> {
        if (cardNumber != null) {
            return transactionDao.getAllTransactions(cardNumber).entries.associate { (transaction, customer) -> transaction.toTransactionModel() to customer.toCustomerModel() }
        }
        return transactionDao.getAllTransactions().entries.associate { (transaction, customer) -> transaction.toTransactionModel() to customer.toCustomerModel() }
    }

    override suspend fun getTransactionsByType(cardNumber: String?, types: List<String>): Map<TransactionModel, CustomerModel> {
        if (cardNumber != null) {
            return transactionDao.getTransactionsByType(cardNumber, types).entries.associate { (transaction, customer) -> transaction.toTransactionModel() to customer.toCustomerModel() }
        }
        return transactionDao.getTransactionsByType(types).entries.associate { (transaction, customer) -> transaction.toTransactionModel() to customer.toCustomerModel() }
    }

    override suspend fun getTransactionsByKeyword(cardNumber: String?, keyword: String): Map<TransactionModel, CustomerModel> {
        if (cardNumber != null) {
            return transactionDao.getTransactionsByKeyword(cardNumber, keyword).entries.associate { (transaction, customer) -> transaction.toTransactionModel() to customer.toCustomerModel() }
        }
        return transactionDao.getTransactionsByKeyword(keyword).entries.associate { (transaction, customer) ->
            transaction.toTransactionModel() to customer.toCustomerModel()
        }
    }

    override suspend fun getTransactionsByKeywordAndType(cardNumber: String?, keyword: String, types: List<String>): Map<TransactionModel, CustomerModel> {
        if(cardNumber != null) {
            return transactionDao.getTransactionsByKeywordAndType(cardNumber, keyword, types).entries.associate { (transaction, customer) -> transaction.toTransactionModel() to customer.toCustomerModel() }
        }
        return transactionDao.getTransactionsByKeywordAndType(keyword, types).entries.associate { (transaction, customer) ->
            transaction.toTransactionModel() to customer.toCustomerModel()
        }
    }

    override suspend fun deleteTransactions() {
        transactionDao.deleteTransactions()
    }
}