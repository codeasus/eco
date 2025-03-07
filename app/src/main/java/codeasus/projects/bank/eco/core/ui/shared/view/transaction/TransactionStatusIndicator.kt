package codeasus.projects.bank.eco.core.ui.shared.view.transaction

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme
import codeasus.projects.bank.eco.domain.local.model.enums.TransactionStatus

@Composable
fun TransactionStatusIndicator(transactionStatus: TransactionStatus) {
    val colorStatus = when (transactionStatus) {
        TransactionStatus.PENDING -> TransactionUIItemColors.COLOR_PENDING
        TransactionStatus.COMPLETED -> TransactionUIItemColors.COLOR_COMPLETED
        TransactionStatus.FAILED -> TransactionUIItemColors.COLOR_FAILED
        TransactionStatus.CANCELED -> TransactionUIItemColors.COLOR_CANCELED
    }
    Box(
        modifier = Modifier
            .drawBehind {
                drawCircle(color = colorStatus.copy(alpha = 0.5F), radius = 15F)
                drawCircle(color = colorStatus, radius = 10F)
            }
            .size(15.dp)
    )
}

@Preview(showSystemUi = false, showBackground = false)
@Composable
fun TransactionStatusIndicatorPreview() {
    EcoTheme {
        TransactionStatusIndicator(transactionStatus = TransactionStatus.FAILED)
    }
}