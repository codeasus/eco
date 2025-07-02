package codeasus.projects.bank.eco.feature.search_transaction.states

import codeasus.projects.bank.eco.domain.local.model.customer.CustomerModel
import codeasus.projects.bank.eco.domain.local.model.enums.TransactionType
import codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel

data class SearchTransactionState(
    val isLoading: Boolean = false,
    var isSearchTextVisible: Boolean = false,
    var searchText: String = "",
    val selectedTransactionTypes: Map<TransactionType, Boolean> = emptyMap(),
    val transactions: List<Pair<CustomerModel, TransactionModel>> = emptyList(),
    val error: String? = null
)