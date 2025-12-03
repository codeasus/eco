package codeasus.projects.bank.eco.feature.home.presentation.states

import codeasus.projects.bank.eco.core.ui.shared.view.models.BankAccountUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.CustomerUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.TransactionUi
import codeasus.projects.bank.eco.core.ui.shared.view.states.BankAccountUiState

data class HomeState(
    val transactions: List<Pair<CustomerUi, TransactionUi>> = emptyList(),
    val bankAccountsUiState: BankAccountUiState<List<BankAccountUi>> = BankAccountUiState.Idle,
)