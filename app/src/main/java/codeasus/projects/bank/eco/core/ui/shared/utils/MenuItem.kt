package codeasus.projects.bank.eco.core.ui.shared.utils

import androidx.annotation.DrawableRes

interface MenuItem {
    val title: String
    @get:DrawableRes
    val iconRes: Int
}