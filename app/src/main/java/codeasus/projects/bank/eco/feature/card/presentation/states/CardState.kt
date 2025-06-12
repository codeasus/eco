package codeasus.projects.bank.eco.feature.card.presentation.states

import codeasus.projects.bank.eco.domain.local.model.user.UserBankAccountModel

data class CardState(
    val isLoading: Boolean = false,
    val bankAccount: UserBankAccountModel? = null,
    val cardFlipState: CardFlipState = CardFlipState.FRONT,
    val showBottomSheet: Boolean = false,
    val error: String? = null
)
