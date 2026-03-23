package codeasus.projects.bank.eco.core.ui.shared.view.card

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CircularCardLayout(
    modifier: Modifier = Modifier,
    radius: Float,
    cardWidth: Dp,
    cardHeight: Dp,
    currentIndex: Animatable<Float, AnimationVector1D>,
    content: @Composable () -> Unit
) {
    Layout(modifier = modifier, content = content) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints.copy(minWidth = 0, minHeight = 0))
        }

        val desiredWidth = if (constraints.hasBoundedWidth) constraints.maxWidth else (radius * 2 + cardWidth.toPx()).toInt()

        val desiredHeight = (cardHeight.toPx() * 1.5f).toInt()

        layout(desiredWidth, desiredHeight) {
            val anglePerCard = 360f / placeables.size

            val layoutCenterX = desiredWidth / 2f

            val layoutCenterY = (desiredHeight / 2f) + radius

            placeables.forEachIndexed { index, placeable ->
                val angleDegrees = -90f + (index + currentIndex.value) * anglePerCard
                val angleRadians = Math.toRadians(angleDegrees.toDouble())

                val cardCenterX = layoutCenterX + radius * cos(angleRadians).toFloat()
                val cardCenterY = layoutCenterY + radius * sin(angleRadians).toFloat()

                val verticalPosition = -sin(angleRadians).toFloat()

                val alphaValue = ((verticalPosition - 0.6f) / 0.4f).coerceIn(0f, 1f)

                if (alphaValue > 0f) {
                    placeable.placeRelativeWithLayer(
                        x = (cardCenterX - placeable.width / 2).toInt(),
                        y = (cardCenterY - placeable.height / 2).toInt()
                    ) {
                        alpha = alphaValue
                        val scale = 0.8f + (0.2f * alphaValue)
                        scaleX = scale
                        scaleY = scale
                    }
                }
            }
        }
    }
}