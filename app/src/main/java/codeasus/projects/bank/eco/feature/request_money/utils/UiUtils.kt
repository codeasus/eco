package codeasus.projects.bank.eco.feature.request_money.utils

import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun bottomBorderShape(lineThicknessDp: Dp): Shape {
    val lineThicknessPx = with(LocalDensity.current) { lineThicknessDp.toPx() }
    return GenericShape { size, _ ->
        moveTo(0f, size.height)
        lineTo(size.width, size.height)
        lineTo(size.width, size.height - lineThicknessPx)
        lineTo(0f, size.height - lineThicknessPx)
    }
}

fun Modifier.requestMonetBottomSheetBackground(color: Color): Modifier {
    return this.drawBehind {
        val width = size.width
        val height = size.height


        val ribbon1 = Path().apply {
            moveTo(0f, height * 0.1f)

            cubicTo(
                width * 0.3f, height * 0.1f,
                width * 0.5f, height * 0.6f,
                width, height * 0.2f
            )

            lineTo(width, height * 0.4f)

            cubicTo(
                width * 0.5f, height * 0.9f,
                width * 0.3f, height * 0.4f,
                0f, height
            )
            close()
        }
        drawPath(
            path = ribbon1,
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color.Transparent,
                    color.copy(alpha = 0.3F)
                )
            )
        )

        val ribbon3 = Path().apply {
            moveTo(0f, height * 0.25f)

            cubicTo(
                width * 0.4f, height * 0.1f,
                width * 0.7f, height * 0.7f,
                width, height * 0.4f
            )

            lineTo(width, height * 1.2f)

            cubicTo(
                width * 0.8f, height * 0.8f,
                width * 0.1f, height * 0.1f,
                0f, height * 1.2f
            )
            close()
        }
        drawPath(
            path = ribbon3,
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color.Transparent,
                    color.copy(alpha = 0.7F)
                )
            )
        )
    }
}