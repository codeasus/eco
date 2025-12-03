package codeasus.projects.bank.eco.feature.search_transaction.states

import codeasus.projects.bank.eco.core.ui.shared.view.models.CustomerUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.TransactionUi
import codeasus.projects.bank.eco.domain.local.model.customer.CustomerModel
import codeasus.projects.bank.eco.domain.local.model.enums.TransactionType
import codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel

data class SearchTransactionState(
    var isSearchTextVisible: Boolean = false,
    var searchText: String = "",
    val selectedTransactionTypes: Map<TransactionType, Boolean> = emptyMap(),
    val transactions: List<Pair<CustomerUi, TransactionUi>> = emptyList(),
)