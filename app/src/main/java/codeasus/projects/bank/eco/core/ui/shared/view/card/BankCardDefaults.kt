package codeasus.projects.bank.eco.core.ui.shared.view.card

import androidx.compose.ui.graphics.Color

object BankCardDefaults {
    const val CORNER_RADIUS = 32
    const val OUTER_RIM_SIZE = 8

    interface ThemedColors {
        val colorBackground: Color
        val colorTextDarker: Color
        val colorTextLighter: Color
        val colorBackgroundShape: Int
    }

    object NormalCardColors : ThemedColors {
        override val colorBackground = Color(31, 23, 80)
        override val colorTextDarker = Color(148, 148, 159, 255)
        override val colorTextLighter = Color.White
        override val colorBackgroundShape = android.graphics.Color.argb(120, 255, 255, 255)
    }

    object PlatinumCardColors : ThemedColors {
        override val colorBackground = Color(231, 231, 246, 255)
        override val colorTextDarker = Color(31, 23, 80)
        override val colorTextLighter = Color(66, 24, 119, 255)
        override val colorBackgroundShape = android.graphics.Color.argb(255, 31, 23, 80)
    }
}