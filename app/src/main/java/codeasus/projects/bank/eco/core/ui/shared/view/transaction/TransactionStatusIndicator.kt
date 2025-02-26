package codeasus.projects.bank.eco.core.ui.shared.view.transaction

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.unit.dp

@Composable
fun TransactionStatusIndicator(transactionStatus: codeasus.projects.bank.eco.domain.local.model.enums.TransactionStatus) {
    val colorStatus = when (transactionStatus) {
        codeasus.projects.bank.eco.domain.local.model.enums.TransactionStatus.PENDING -> TransactionUIItemColors.COLOR_PENDING
        codeasus.projects.bank.eco.domain.local.model.enums.TransactionStatus.COMPLETED -> TransactionUIItemColors.COLOR_COMPLETED
        codeasus.projects.bank.eco.domain.local.model.enums.TransactionStatus.FAILED -> TransactionUIItemColors.COLOR_FAILED
        codeasus.projects.bank.eco.domain.local.model.enums.TransactionStatus.CANCELED -> TransactionUIItemColors.COLOR_CANCELED
    }
    Box(
        modifier = Modifier
            .drawBehind {
                drawCircle(color = colorStatus.copy(alpha = 0.5F), radius = 15F)
                drawCircle(color = colorStatus, radius = 10F)
            }
            .size(20.dp)
    )
}