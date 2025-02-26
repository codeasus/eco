package codeasus.projects.bank.eco.data.local.repository.transaction

import codeasus.projects.bank.eco.data.local.dao.TransactionDao
import codeasus.projects.bank.eco.data.local.mappers.toTransactionEntity
import codeasus.projects.bank.eco.data.local.mappers.toTransactionModel
import codeasus.projects.bank.eco.domain.local.repository.transaction.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao
) : TransactionRepository {

    override suspend fun saveTransaction(transaction: codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel) {
        transactionDao.insertTransaction(transaction.toTransactionEntity())
    }

    override suspend fun getTransactionById(id: String): codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel? {
        return transactionDao.getTransactionById(id)?.toTransactionModel()
    }

    override fun getAllTransactions(): Flow<List<codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel>> {
        return transactionDao.getAllTransactions().map { entities ->
            entities.map { it.toTransactionModel() }
        }
    }
}