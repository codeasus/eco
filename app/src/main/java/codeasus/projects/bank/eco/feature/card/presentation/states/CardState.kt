package codeasus.projects.bank.eco.feature.card.presentation.states

import codeasus.projects.bank.eco.core.ui.shared.view.models.BankAccountUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.TransactionUi
import codeasus.projects.bank.eco.feature.home.presentation.states.TransactionListItemUI
import codeasus.projects.bank.eco.feature.utils.UiState

data class CardState(
    val bankAccountId: String? = null,
    val bankAccountUiState: UiState<BankAccountUi> = UiState.Empty,
    val bankAccountPrivateDataUiState: UiState<BankAccountUi> = UiState.Empty,
    val transactionUiState: UiState<TransactionUi> = UiState.Empty,
    val transactions: List<TransactionListItemUI> = emptyList(),
    val cardFlipState: CardFlipState = CardFlipState.FRONT,
    val showCardDetailsBottomSheet: Boolean = false,
    val showTransactionBottomSheet: Boolean = false
)