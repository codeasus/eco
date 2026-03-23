package codeasus.projects.bank.eco.core.ui.shared.view.card

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import codeasus.projects.bank.eco.core.ui.shared.view.models.BankAccountUi
import codeasus.projects.bank.eco.core.ui.shared.view.utils.DataSourceDefaults
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme
import kotlinx.coroutines.launch

@Composable
fun CircularShufflingCards(cards: List<BankAccountUi>, onCardSelected: (Int) -> Unit) {
    val hapticFeedback = LocalHapticFeedback.current

    val cardWidth = 128.dp
    val cardHeight = 72.dp

    val animatableOffset = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()


    var activeIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(activeIndex) {
        onCardSelected(activeIndex % cards.size)
    }

    CircularCardLayout(
        modifier = Modifier
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = { },
                    onHorizontalDrag = { change, dragAmount ->
                        change.consume()
                        if (!animatableOffset.isRunning) {
                            scope.launch {
                                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                                if (dragAmount > 0) {
                                    animatableOffset.animateTo(
                                        targetValue = animatableOffset.value + 1f,
                                        animationSpec = tween(durationMillis = 300)
                                    )
                                    if (activeIndex == 0) {
                                        activeIndex = cards.size
                                    }
                                    activeIndex--
                                } else if (dragAmount < 0) {
                                    animatableOffset.animateTo(
                                        targetValue = animatableOffset.value - 1f,
                                        animationSpec = tween(durationMillis = 300)
                                    )
                                    if (activeIndex == cards.size) {
                                        activeIndex = 0
                                    }
                                    activeIndex++
                                }
                            }
                        }
                    }
                )
            },
        currentIndex = animatableOffset,
        radius = 360f,
        cardWidth = cardWidth,
        cardHeight = cardHeight
    ) {
        cards.forEachIndexed { index, card ->
            val anglePerCard = 360f / cards.size
            val currentCardAngle = (index + animatableOffset.value) * anglePerCard
            CardMini(
                modifier = Modifier
                    .width(cardWidth)
                    .height(cardHeight),
                bankAccountUi = card,
                rotation = currentCardAngle
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CircularShuffleCardPreview() {
    EcoTheme {
        CircularShufflingCards(
            listOf(
                DataSourceDefaults.unknownUser.second[1],
                DataSourceDefaults.unknownUser.second[0]
            )
        ) {

        }
    }
}