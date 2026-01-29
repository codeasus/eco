package codeasus.projects.bank.eco.feature.search_transaction.states

data class SearchParametersState(
    val bankAccountId: String? = null,
    val searchText: String = "",
    val selectedTransactionTypes: List<String> = listOf()
)