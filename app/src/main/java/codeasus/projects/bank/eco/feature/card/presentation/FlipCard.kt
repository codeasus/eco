package codeasus.projects.bank.eco.feature.card.presentation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import codeasus.projects.bank.eco.core.ui.shared.view.card.BankCardBack
import codeasus.projects.bank.eco.core.ui.shared.view.card.BankCardFront
import codeasus.projects.bank.eco.core.ui.shared.view.models.BankAccountUi
import codeasus.projects.bank.eco.feature.card.presentation.states.CardFlipState

@Composable
fun FlipCard(cardFlipState: CardFlipState, bankAccountUi: BankAccountUi) {
    val rotationAnimation = animateFloatAsState(
        targetValue = cardFlipState.angle,
        animationSpec = tween(
            durationMillis = 1000,
            easing = FastOutSlowInEasing,
        )
    )

    if (rotationAnimation.value >= 90.0F) {
        BankCardBack(
            modifier = Modifier.fillMaxWidth().height(240.dp),
            bankAccountUi = bankAccountUi,
            rotationYAngle = rotationAnimation.value
        )
    } else {
        BankCardFront(
            modifier = Modifier.fillMaxWidth().height(240.dp),
            bankAccountUi = bankAccountUi,
            rotationYAngle = rotationAnimation.value
        )
    }
}