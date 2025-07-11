package codeasus.projects.bank.eco.feature.profile.presentation.utils

import codeasus.projects.bank.eco.R
import codeasus.projects.bank.eco.core.ui.shared.utils.MenuItem

sealed class AccountMenuItem(override val title: String, override val iconRes: Int) : MenuItem {
    class Help : AccountMenuItem("Help", R.drawable.ic_question)
    class Account : AccountMenuItem("Account", R.drawable.ic_account)
    class Documents : AccountMenuItem("Documents & Statements", R.drawable.ic_document)
    class Learn : AccountMenuItem("Learn", R.drawable.ic_bulb)
}