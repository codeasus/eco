package codeasus.projects.bank.eco.feature.search_transaction.states

import codeasus.projects.bank.eco.core.ui.shared.view.models.TransactionUi
import codeasus.projects.bank.eco.domain.local.model.enums.TransactionType
import codeasus.projects.bank.eco.feature.home.presentation.states.TransactionListItemUI
import codeasus.projects.bank.eco.feature.utils.UiState
import java.util.Collections.emptyMap

data class SearchTransactionState(
    var isSearchTextVisible: Boolean = false,
    var searchParametersState: SearchParametersState = SearchParametersState(),
    val selectedTransactionTypes: Map<TransactionType, Boolean> = emptyMap(),
    val transactionUiState: UiState<TransactionUi> = UiState.Empty,
    val transactions: List<TransactionListItemUI> = emptyList(),
    val showBottomSheet: Boolean = false
)