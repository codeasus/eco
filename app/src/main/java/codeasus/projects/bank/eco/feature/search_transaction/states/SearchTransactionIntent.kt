package codeasus.projects.bank.eco.feature.search_transaction.states

import codeasus.projects.bank.eco.domain.local.model.enums.TransactionType

sealed class SearchTransactionIntent {
    data object ToggleSearchTextVisibility : SearchTransactionIntent()
    data class SetSearchText(val text: String) : SearchTransactionIntent()
    data class SelectTransactionType(val type: TransactionType) : SearchTransactionIntent()
    data class ShowBottomSheet(val transactionId: String) : SearchTransactionIntent()
    data object HideBottomSheet : SearchTransactionIntent()
}