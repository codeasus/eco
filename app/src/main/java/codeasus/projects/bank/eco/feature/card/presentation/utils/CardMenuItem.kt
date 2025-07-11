package codeasus.projects.bank.eco.feature.card.presentation.utils

import androidx.annotation.DrawableRes
import codeasus.projects.bank.eco.R
import codeasus.projects.bank.eco.core.ui.shared.utils.MenuItem

sealed class CardMenuItem(override val title: String, @DrawableRes override val iconRes: Int) : MenuItem {
    class CardControls : CardMenuItem("Card Controls", R.drawable.ic_controls)
    class Transactions : CardMenuItem("Transactions", R.drawable.ic_transaction)
    class EditCard : CardMenuItem("Edit Card", R.drawable.ic_edit_card)
    class DeleteCard : CardMenuItem("Delete Card", R.drawable.ic_delete_card)
    class Limits : CardMenuItem("Limits", R.drawable.ic_limit)
}