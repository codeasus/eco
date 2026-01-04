package codeasus.projects.bank.eco.feature.card.presentation.states

sealed class CardIntent {
    data object FlipCard : CardIntent()
    data object More: CardIntent()
    data object ShowCardDetailsBottomSheet : CardIntent()
    data object HideCardDetailsBottomSheet : CardIntent()
    data class ShowTransactionBottomSheet(val transactionId: String) : CardIntent()
    data object HideTransactionBottomSheet : CardIntent()
    data object TopUp : CardIntent()
    data class LoadCard(val bankAccountId: String) : CardIntent()
}