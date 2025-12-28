package codeasus.projects.bank.eco.feature.card.presentation

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path

val lightBankCardContainerColor = Color(184, 193, 239, 255)
val darkerBankAccountContainerColor = Color(93, 95, 138, 255)
val frontWaveColor = Color(113, 119, 164, 255)
val middleWaveColor = Color(80, 82, 129, 255)
val backWaveColor = Color(54, 63, 110, 255)

fun Modifier.backgroundCard(): Modifier {
    return drawBehind {
        fun Path.applyBottomCurve(width: Float, height: Float) {
            cubicTo(
                x1 = width * 0.75F,
                y1 = height - 150F,
                x2 = width * 0.4F,
                y2 = height + 200F,
                x3 = 0F,
                y3 = height
            )
        }

        val mainBackground = Path().apply {
            moveTo(0F, 0F)
            lineTo(size.width, 0F)
            lineTo(size.width, size.height)
            applyBottomCurve(size.width, size.height)
            close()
        }

        val bgWaveFirst = Path().apply {
            moveTo(0F, size.height - 50F)
            cubicTo(
                x1 = size.width * 0.4f, y1 = size.height,
                x2 = size.width * 0.7f, y2 = size.height - 650F,
                x3 = size.width, y3 = size.height - 550F
            )
            lineTo(size.width, size.height)
            applyBottomCurve(size.width, size.height)
            close()
        }

        val bgWaveSecond = Path().apply {
            moveTo(0F, size.height - 30F)
            cubicTo(
                x1 = size.width * 0.3f, y1 = size.height,
                x2 = size.width * 0.8f, y2 = size.height - 450F,
                x3 = size.width, y3 = size.height - 400F
            )
            lineTo(size.width, size.height)
            applyBottomCurve(size.width, size.height)
            close()
        }

        val bgWaveThird = Path().apply {
            moveTo(0F, size.height - 10F)
            cubicTo(
                x1 = size.width * 0.3f, y1 = size.height + 50F,
                x2 = size.width * 0.9f, y2 = size.height - 250F,
                x3 = size.width, y3 = size.height - 250F
            )
            lineTo(size.width, size.height)
            applyBottomCurve(size.width, size.height)
            close()
        }

        drawPath(
            path = mainBackground,
            brush = Brush.verticalGradient(
                colors = listOf(
                    darkerBankAccountContainerColor,
                    lightBankCardContainerColor
                )
            )
        )

        drawPath(
            path = bgWaveFirst,
            brush = Brush.horizontalGradient(
                colors = listOf(
                    lightBankCardContainerColor,
                    frontWaveColor
                )
            )
        )

        drawPath(
            path = bgWaveSecond,
            brush = Brush.horizontalGradient(
                colors = listOf(
                    lightBankCardContainerColor,
                    middleWaveColor
                )
            )
        )

        drawPath(
            path = bgWaveThird,
            brush = Brush.horizontalGradient(
                colors = listOf(
                    lightBankCardContainerColor,
                    backWaveColor
                )
            )
        )
    }
}