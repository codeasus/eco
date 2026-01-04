package codeasus.projects.bank.eco.feature.search_transaction.states

import codeasus.projects.bank.eco.core.ui.shared.view.models.CustomerUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.TransactionUi
import codeasus.projects.bank.eco.domain.local.model.enums.TransactionType
import codeasus.projects.bank.eco.feature.utils.UiState

data class SearchTransactionState(
    var isSearchTextVisible: Boolean = false,
    var searchText: String = "",
    val selectedTransactionTypes: Map<TransactionType, Boolean> = emptyMap(),
    val transactionUiState: UiState<TransactionUi> = UiState.Empty,
    val transactions: List<Pair<CustomerUi, TransactionUi>> = emptyList(),
    val showBottomSheet: Boolean = false
)