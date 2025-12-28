package codeasus.projects.bank.eco.feature.home.presentation.states

import codeasus.projects.bank.eco.core.ui.shared.mappers.toBankAccountUi
import codeasus.projects.bank.eco.core.ui.shared.mappers.toUserUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.BankAccountUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.CustomerUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.TransactionUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.UserUi
import codeasus.projects.bank.eco.core.ui.shared.view.states.BankAccountUiState
import codeasus.projects.bank.eco.core.ui.shared.view.utils.DataSourceDefaults

data class HomeState(
    val user: UserUi = DataSourceDefaults.unknownUser.first.toUserUi(),
    val currentBankAccount: BankAccountUi = DataSourceDefaults.defaultUserBankAccountModel.toBankAccountUi(),
    val transactions: List<Pair<CustomerUi, TransactionUi>> = emptyList(),
    val bankAccountsUiState: BankAccountUiState<List<BankAccountUi>> = BankAccountUiState.Idle,
)