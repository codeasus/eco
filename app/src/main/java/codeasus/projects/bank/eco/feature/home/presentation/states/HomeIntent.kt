package codeasus.projects.bank.eco.feature.home.presentation.states

sealed class HomeIntent {
    data object RestackCards : HomeIntent()
    data class ShowBottomSheet(val transactionId: String) : HomeIntent()
    data object HideBottomSheet : HomeIntent()
}