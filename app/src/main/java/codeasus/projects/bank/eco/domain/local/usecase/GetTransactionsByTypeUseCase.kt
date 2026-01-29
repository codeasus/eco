package codeasus.projects.bank.eco.domain.local.usecase

import codeasus.projects.bank.eco.core.ui.shared.view.utils.formatTransactionListDate
import codeasus.projects.bank.eco.domain.local.model.transaction.TransactionListItem
import codeasus.projects.bank.eco.domain.local.repository.transaction.TransactionRepository
import javax.inject.Inject
import kotlin.collections.iterator

class GetTransactionsByTypeUseCase @Inject constructor(private val transactionRepository: TransactionRepository) {
    suspend operator fun invoke(accountId: String? = null, types: List<String>): List<TransactionListItem> {
        val transactions = transactionRepository.getTransactionsByType(accountId = accountId, types = types)

        val result = mutableListOf<TransactionListItem>()
        var lastDate: String? = null

        for (transactionWithCustomer in transactions) {
            val updatedTransactionDate = transactionWithCustomer.key.updatedAt

            val date: String = formatTransactionListDate(updatedTransactionDate)

            if (date != lastDate) {
                result += TransactionListItem.TransactionDateItem(date)
                lastDate = date
            }

            result += TransactionListItem.TransactionItem(mapOf(transactionWithCustomer.key to transactionWithCustomer.value))
        }

        return result
    }
}