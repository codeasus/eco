package codeasus.projects.bank.eco.feature.home.presentation.states

import codeasus.projects.bank.eco.core.ui.shared.view.models.BankAccountUi
import codeasus.projects.bank.eco.core.ui.shared.view.states.BankAccountUiState
import codeasus.projects.bank.eco.domain.local.model.customer.CustomerModel
import codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel

data class HomeState(
    val isLoading: Boolean = false,
    val transactions: List<Pair<CustomerModel, TransactionModel>> = emptyList(),
    val bankAccountsUiState: BankAccountUiState<List<BankAccountUi>> = BankAccountUiState.Idle,
    val error: String? = null
)