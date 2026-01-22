package codeasus.projects.bank.eco.feature.home.presentation.states

import codeasus.projects.bank.eco.core.ui.shared.mappers.toBankAccountUi
import codeasus.projects.bank.eco.core.ui.shared.mappers.toCustomerUi
import codeasus.projects.bank.eco.core.ui.shared.mappers.toTransactionUi
import codeasus.projects.bank.eco.core.ui.shared.mappers.toUserUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.BankAccountUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.CustomerUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.TransactionUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.UserUi
import codeasus.projects.bank.eco.core.ui.shared.view.states.BankAccountUiState
import codeasus.projects.bank.eco.core.ui.shared.view.utils.DataSourceDefaults
import codeasus.projects.bank.eco.domain.local.model.transaction.TransactionListItem
import codeasus.projects.bank.eco.feature.utils.UiState

data class HomeState(
    val user: UserUi = DataSourceDefaults.unknownUser.first.toUserUi(),
    val currentBankAccount: BankAccountUi = DataSourceDefaults.defaultUserBankAccountModel.toBankAccountUi(),
    val transactionUiState: UiState<TransactionUi> = UiState.Empty,
    val transactions: List<TransactionListItemUI> = emptyList(),
    val bankAccountsUiState: BankAccountUiState<List<BankAccountUi>> = BankAccountUiState.Idle,
    val showBottomSheet: Boolean = false
)

sealed class TransactionListItemUI {
    data class TransactionDateItem(val date: String) : TransactionListItemUI()
    data class TransactionItem(val transactionWithCustomer:  Pair<CustomerUi, TransactionUi>) : TransactionListItemUI()
}

fun TransactionListItem.toTransactionDateItemUI(): TransactionListItemUI {
    return when (this) {
        is TransactionListItem.TransactionItem -> {
            val (transactionModel, customerModel) = this.transactionWithCustomer.entries.first()
            TransactionListItemUI.TransactionItem(Pair(customerModel.toCustomerUi(), transactionModel.toTransactionUi()))
        }

        is TransactionListItem.TransactionDateItem -> {
            TransactionListItemUI.TransactionDateItem(this.date)
        }
    }
}