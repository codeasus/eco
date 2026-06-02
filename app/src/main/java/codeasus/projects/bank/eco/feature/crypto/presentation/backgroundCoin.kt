package codeasus.projects.bank.eco.feature.crypto.presentation

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path

val lightBankCardContainerColor = Color(164, 179, 234, 255)
val darkerBankAccountContainerColor = Color(99, 95, 159, 255)

fun Modifier.backgroundCoin(): Modifier {
    return this.drawBehind {

        fun Path.applyBottomCurve(width: Float, height: Float) {
            cubicTo(
                x1 = width * 0.3F, y1 = height - 250F,
                x2 = width * 0.3F, y2 = height + 250F,
                x3 = 0F, y3 = height
            )
        }

        val mainBackground = Path().apply {
            moveTo(0F, 0F)
            lineTo(size.width, 0F)
            lineTo(size.width, size.height)
            applyBottomCurve(size.width, size.height)
            close()
        }

        val firstWave = Path().apply {
            moveTo(0F, size.height - 350F)
            cubicTo(
                x1 = size.width * 0.5F, y1 = size.height - 250F,
                x2 = size.width * 0.4F, y2 = size.height + 150.0F,
                x3 = size.width, y3 = 0.0F + 450F
            )
            lineTo(size.width, size.height)
            applyBottomCurve(size.width, size.height)
            close()
        }

        val secondWave = Path().apply {
            moveTo(0F, size.height - 550F)
            cubicTo(
                x1 = size.width * 0.5F, y1 = size.height - 450F,
                x2 = size.width * 0.4F, y2 = size.height - 50.0F,
                x3 = size.width, y3 = 0.0F + 400F
            )
            lineTo(size.width, size.height)
            applyBottomCurve(size.width, size.height)
            close()
        }

        val thirdWave = Path().apply {
            moveTo(0F, size.height - 650F)
            cubicTo(
                x1 = size.width * 0.5F, y1 = size.height - 650F,
                x2 = size.width * 0.4F, y2 = size.height + 50.0F,
                x3 = size.width, y3 = 0.0F + 250F
            )
            lineTo(size.width, size.height)
            applyBottomCurve(size.width, size.height)
            close()
        }

        val fourthWave = Path().apply {
            moveTo(0F, size.height - 100F)
            lineTo(x = 0F, y = size.height)
            cubicTo(
                x1 = 0F, y1 = size.height - 100F,
                x2 = size.width / 2 + 100F, y2 = size.height + 200.0F,
                x3 = size.width, y3 = size.height - 50F
            )
            lineTo(x = size.width, y = size.height - 100f)
            close()
        }

        drawPath(
            path = fourthWave,
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(85, 101, 187, 255),
                    Color(164, 179, 234, 255)
                ), startY = size.height * .8f, endY = size.height
            )
        )

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
            path = thirdWave,
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(102, 105, 169, 255),
                    Color(217, 218, 253, 255)
                ), size.height * .5F
            )
        )

        drawPath(
            path = secondWave,
            brush = Brush.verticalGradient(
                colors = listOf(
                    darkerBankAccountContainerColor,
                    Color(190, 197, 255, 255)
                ), size.height * .5F
            )
        )

        drawPath(
            path = firstWave,
            brush = Brush.verticalGradient(
                colors = listOf(
                    darkerBankAccountContainerColor,
                    lightBankCardContainerColor
                ), size.height * .5F
            )
        )
    }
}