package codeasus.projects.bank.eco.feature.profile.presentation.utils

import codeasus.projects.bank.eco.R
import codeasus.projects.bank.eco.core.ui.shared.utils.MenuItem

sealed class AppMenuItem(override val title: String, override val iconRes: Int) : MenuItem {
    class Security : AppMenuItem("Security", R.drawable.ic_security)
    class Notifications : AppMenuItem("Notifications", R.drawable.ic_notification)
    class Appearance : AppMenuItem("Appearance", R.drawable.ic_palette)
    class Accessibility : AppMenuItem("Accessibility", R.drawable.ic_accessibility)
}