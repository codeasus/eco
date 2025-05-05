package codeasus.projects.bank.eco.feature.card.presentation.states

enum class CardFlipState(val angle: Float) {
    FRONT(0.0F),
    BACK(180.0F);

    val next: CardFlipState
        get() = when (this) {
            FRONT -> BACK
            BACK -> FRONT
        }
}