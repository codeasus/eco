package codeasus.projects.bank.eco.core.ui.shared.view.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import codeasus.projects.bank.eco.core.ui.shared.view.ImgProfile
import codeasus.projects.bank.eco.core.ui.shared.view.models.CustomerUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.TransactionUi
import codeasus.projects.bank.eco.core.ui.shared.view.utils.DataSourceDefaults
import codeasus.projects.bank.eco.core.ui.shared.view.utils.defineTransactionAmountColor
import codeasus.projects.bank.eco.core.ui.shared.view.utils.formatUITransactionAmount
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme
import codeasus.projects.bank.eco.domain.local.model.enums.TransactionStatus

@Composable
fun TransactionListItem(customerTransactionPair: Pair<CustomerUi, TransactionUi>) {

    val statusColor = when (customerTransactionPair.second.status) {
        TransactionStatus.PENDING -> TransactionUIItemColors.COLOR_PENDING
        TransactionStatus.COMPLETED -> TransactionUIItemColors.COLOR_COMPLETED
        TransactionStatus.FAILED -> TransactionUIItemColors.COLOR_FAILED
        TransactionStatus.CANCELED -> TransactionUIItemColors.COLOR_CANCELED
    }

    Surface(
        modifier = Modifier
            .height(64.dp)
            .fillMaxWidth(),
        color = Color.Transparent
    ) {
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
//            if (customerTransactionPair.second.status == TransactionStatus.COMPLETED) {
//                Box(
//                    modifier = Modifier
//                        .fillMaxHeight()
//                        .width(16.dp)
//                        .clip(RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp))
//                        .background(
//                            Brush.horizontalGradient(
//                                listOf(
//                                    statusColor.copy(alpha = 0.4F),
//                                    Color.Transparent
//                                )
//                            )
//                        )
//                )
//            }
            Row(
                modifier = Modifier
                    .adaptTransactionListBackgroundToTransactionStatus(
                        transactionStatus = customerTransactionPair.second.status,
                        statusColor
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                ImgProfile(
                    imageModifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape),
                    profileImageResId = customerTransactionPair.first.profileImg
                )
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 4.dp),
                        text = customerTransactionPair.first.name,
                        style = TextStyle(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = customerTransactionPair.second.updatedAt,
                        style = TextStyle(color = LocalContentColor.current.copy(alpha = 0.4f)),
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = formatUITransactionAmount(customerTransactionPair.second),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = defineTransactionAmountColor(customerTransactionPair.second.type)
                )
            )
        }
    }
}

private fun Modifier.adaptTransactionListBackgroundToTransactionStatus(
    transactionStatus: TransactionStatus,
    color: Color
): Modifier {
    if (transactionStatus != TransactionStatus.COMPLETED) {
        return this
            .clip(RoundedCornerShape(topStart = 24.dp, bottomStart = 24.dp))
            .background(
                Brush.horizontalGradient(
                    listOf(
                        color.copy(alpha = 0.4F),
                        Color.Transparent
                    )
                )
            )
    }
    return Modifier
}

@Preview(showSystemUi = false, showBackground = false)
@Composable
fun TransactionListItemPreview() {
    EcoTheme {
        TransactionListItem(
            Pair(
                DataSourceDefaults.getCustomers()[1],
                DataSourceDefaults.getTransactions()[1]
            )
        )
    }
}