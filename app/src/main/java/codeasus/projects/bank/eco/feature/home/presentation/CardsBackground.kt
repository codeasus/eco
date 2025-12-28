package codeasus.projects.bank.eco.feature.home.presentation

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path

val lightBankCardContainerColor = Color(184, 193, 239, 255)
val darkerBankAccountContainerColor = Color(93, 95, 138, 255)
val mainWaveColor = Color(93, 95, 138, 255)

fun Modifier.backgroundCards(): Modifier {
    return this.drawBehind {
        fun Path.applyBottomCurve(width: Float, height: Float) {
            cubicTo(
                x1 = width * 1.4F,
                y1 = height - 350F,
                x2 = width * 0.4F,
                y2 = height + 300F,
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

        val bgWaveMain = Path().apply {
            moveTo(0F, size.height - 50F)
            cubicTo(
                x1 = size.width * 0.6F, y1 = size.height,
                x2 = size.width * 0.8F, y2 = size.height - 650.0F,
                x3 = size.width, y3 = 0.0F
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
            path = bgWaveMain,
            brush = Brush.horizontalGradient(
                colors = listOf(
                    lightBankCardContainerColor,
                    mainWaveColor
                )
            )
        )
    }
}