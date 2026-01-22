package codeasus.projects.bank.eco.domain.local.usecase

import codeasus.projects.bank.eco.core.ui.shared.view.utils.formatTransactionListDate
import codeasus.projects.bank.eco.domain.local.model.transaction.TransactionListItem
import codeasus.projects.bank.eco.domain.local.repository.transaction.TransactionRepository
import javax.inject.Inject

class GetAllTransactionsListItemsUseCase @Inject constructor(private val transactionRepository: TransactionRepository) {
    suspend operator fun invoke(accountId: String? = null): List<TransactionListItem> {
        val transactionsWithCustomers = transactionRepository.getAllTransactions(accountId)

        val result = mutableListOf<TransactionListItem>()
        var lastDate: String? = null

        for (transactionWithCustomer in transactionsWithCustomers) {
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