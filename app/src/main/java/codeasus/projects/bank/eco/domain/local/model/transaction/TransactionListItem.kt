package codeasus.projects.bank.eco.domain.local.model.transaction

import codeasus.projects.bank.eco.domain.local.model.customer.CustomerModel

sealed class TransactionListItem {
    data class TransactionDateItem(val date: String) : TransactionListItem()
    data class TransactionItem(val transactionWithCustomer:  Map<TransactionModel, CustomerModel>) : TransactionListItem()
}