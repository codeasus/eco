package codeasus.projects.bank.eco.feature.card.presentation.states

sealed class CardIntent {
    data object FlipCard : CardIntent()
    data object ShowBottomSheet : CardIntent()
    data object HideBottomSheet : CardIntent()
    data object FreezeCard : CardIntent()
    data class LoadCard(val bankAccountId: String) : CardIntent()
}