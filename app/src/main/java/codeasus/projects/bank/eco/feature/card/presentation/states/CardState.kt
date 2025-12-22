package codeasus.projects.bank.eco.feature.card.presentation.states

import codeasus.projects.bank.eco.core.ui.shared.view.models.BankAccountUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.CustomerUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.TransactionUi
import codeasus.projects.bank.eco.core.ui.shared.view.states.BankAccountUiState

data class CardState(
    val bankAccountId: String? = null,
    val bankAccountUiState: BankAccountUiState<BankAccountUi> = BankAccountUiState.Idle,
    val bankAccountPrivateDataUiState: BankAccountUiState<BankAccountUi> = BankAccountUiState.Idle,
    val transactions: List<Pair<CustomerUi, TransactionUi>> = emptyList(),
    val cardFlipState: CardFlipState = CardFlipState.FRONT,
    val showBottomSheet: Boolean = false
)