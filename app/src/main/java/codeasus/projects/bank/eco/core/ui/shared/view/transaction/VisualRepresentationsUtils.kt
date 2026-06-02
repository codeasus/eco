package codeasus.projects.bank.eco.core.ui.shared.view.transaction

import androidx.compose.ui.graphics.Color
import codeasus.projects.bank.eco.R
import codeasus.projects.bank.eco.domain.local.model.enums.TransactionStatus

fun getTransactionStatusColorByStatus(transactionStatus: TransactionStatus): Color {
    return when (transactionStatus) {
        TransactionStatus.PENDING -> UiItemColors.COLOR_PENDING
        TransactionStatus.COMPLETED -> UiItemColors.COLOR_MATERIAL_GREEN
        TransactionStatus.FAILED -> UiItemColors.COLOR_FAILED
        TransactionStatus.CANCELED -> UiItemColors.COLOR_CANCELED
    }
}

fun getTransactionStatusIconByStatus(transactionStatus: TransactionStatus): Int {
    return when (transactionStatus) {
        TransactionStatus.PENDING -> R.drawable.ic_pending
        TransactionStatus.COMPLETED -> R.drawable.ic_complete
        TransactionStatus.FAILED -> R.drawable.ic_fail
        TransactionStatus.CANCELED -> R.drawable.ic_cancel
    }
}