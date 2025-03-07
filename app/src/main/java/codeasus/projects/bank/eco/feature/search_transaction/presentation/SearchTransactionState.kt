package codeasus.projects.bank.eco.feature.search_transaction.presentation

data class SearchTransactionState(
    var isSearchTextVisible: Boolean = false,
    val searchActionEngaged: Boolean = false,
    var searchText: String = "",
)
