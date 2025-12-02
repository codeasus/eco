package codeasus.projects.bank.eco.core.ui.shared.view.states

sealed class BankAccountUiState<out T> {
    object Idle : BankAccountUiState<Nothing>()
    object Loading : BankAccountUiState<Nothing>()
    object NotFound : BankAccountUiState<Nothing>()
    data class Success<T>(val data: T) : BankAccountUiState<T>()
}