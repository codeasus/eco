package codeasus.projects.bank.eco.feature.card.presentation.states

import codeasus.projects.bank.eco.core.ui.shared.view.models.BankAccountUi
import codeasus.projects.bank.eco.core.ui.shared.view.states.BankAccountUiState

data class CardState(
    val bankAccountId: Long = 0,
    val bankAccountUiState: BankAccountUiState<BankAccountUi> = BankAccountUiState.Idle,
    val bankAccountPrivateDataUiState: BankAccountUiState<BankAccountUi> = BankAccountUiState.Idle,
    val cardFlipState: CardFlipState = CardFlipState.FRONT,
    val showBottomSheet: Boolean = false,
    val error: String? = null
)