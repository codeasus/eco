package codeasus.projects.bank.eco.feature.home.presentation.states

import codeasus.projects.bank.eco.core.ui.shared.view.models.BankAccountUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.CustomerUi

sealed class HomeIntent {
    data object RestackCards : HomeIntent()
    data class SetTransferAmount(val strAmount: String): HomeIntent()
    data class SetBeneficiaryBankAccount(val bankAccount: BankAccountUi): HomeIntent()
    data class SelectFriend(val friend: CustomerUi): HomeIntent()
    data class ShowTransactionViewBottomSheet(val transactionId: String) : HomeIntent()
    data object HideTransactionBottomBottomSheet : HomeIntent()
    data object ShowRequestMoneyBottomBottomSheet : HomeIntent()
    data object HideRequestMoneyBottomBottomSheet : HomeIntent()
}