package codeasus.projects.bank.eco.core.ui.shared.view.card

import android.content.res.Configuration
import androidx.compose.animation.core.tween
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.snapTo
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.adaptive.currentWindowSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import codeasus.projects.bank.eco.core.ui.shared.view.utils.DataSourceDefaults
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme
import codeasus.projects.bank.eco.domain.local.model.user.UserBankAccountModel
import kotlinx.coroutines.flow.collectLatest
import kotlin.math.abs
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Cards(
    userBankAccounts: List<UserBankAccountModel>,
    onCardSelected: (UserBankAccountModel) -> Unit,
    onCardSwiped: () -> Unit
) {

    val density = LocalDensity.current
    val windowsWidth = currentWindowSize().width.toFloat()
    val topCardAlpha = remember { mutableFloatStateOf(1.0f) }

    val boxDragState = remember {
        AnchoredDraggableState(
            initialValue = DragAnchors.CENTER,
            anchors = DraggableAnchors {
                DragAnchors.CENTER at 0.0f
                DragAnchors.LEFT at 0.0f - windowsWidth
            },
            positionalThreshold = { distance -> distance * 0.5f },
            velocityThreshold = { 125.0f },
            snapAnimationSpec = tween(),
            decayAnimationSpec = splineBasedDecay(density)
        )
    }

    LaunchedEffect(boxDragState) {
        snapshotFlow { boxDragState.settledValue }.collectLatest {
            if (it == DragAnchors.LEFT) {
                boxDragState.snapTo(DragAnchors.CENTER)
                onCardSwiped()
            }
        }
    }
    LaunchedEffect(boxDragState) {
        snapshotFlow { boxDragState.offset }
            .collect { offset ->
                val normalizedOffset = 1 - abs(offset / windowsWidth) * 0.8f
                topCardAlpha.floatValue = normalizedOffset
            }
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        val parentWidth = maxWidth
        val maxCardHeight = 240

        userBankAccounts.forEachIndexed { index, bankAccount ->
            val scaleFactor = 1f - (index * 0.1f)
            val currentWidth = parentWidth * scaleFactor
            var currentHeight = maxCardHeight * scaleFactor

            val remainingTopSpace = 280 - maxCardHeight
            val spaceForEachCard = remainingTopSpace / (userBankAccounts.size - 1).coerceAtLeast(1)
            var offsetY = -(maxCardHeight - currentHeight) - (spaceForEachCard * index)

            if (userBankAccounts.size == 1) {
                currentHeight = 280.0f
                offsetY = 0.0f
            }

            Layout(
                content = {
                    val modifier = Modifier
                        .fillMaxSize()
                        .spec(index, boxDragState)
                        .absoluteOffset(y = offsetY.dp)
                    val bankCardAlpha = if (index == 0) topCardAlpha.floatValue else 1.0f
                    BankCard(
                        modifier = modifier,
                        bankAccount = bankAccount,
                        bankCardAlpha = bankCardAlpha,
                        onCardSelected = onCardSelected
                    )
                },
                modifier = Modifier.zIndex(userBankAccounts.size - index.toFloat()),
                measurePolicy = { measurables, constraints ->
                    val placeable = measurables.first().measure(
                        Constraints.fixed(
                            width = currentWidth.roundToPx(),
                            height = currentHeight.dp.roundToPx()
                        )
                    )
                    layout(constraints.maxWidth, constraints.maxHeight) {
                        val x = (constraints.maxWidth - placeable.width) / 2
                        val y = constraints.maxHeight - placeable.height
                        placeable.placeRelative(x, y)
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Modifier.spec(index: Int, boxDragState: AnchoredDraggableState<DragAnchors>): Modifier {
    if (index == 0) {
        return this
            .offset { IntOffset(boxDragState.requireOffset().roundToInt(), y = 0) }
            .anchoredDraggable(state = boxDragState, orientation = Orientation.Horizontal)
    }
    return Modifier
}

@Preview(
    showSystemUi = false,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun CardsCarouselPreview() {
    EcoTheme {
        Cards(
            userBankAccounts = DataSourceDefaults.unknownUser.bankAccounts,
            onCardSwiped = {},
            onCardSelected = {}
        )
    }
}