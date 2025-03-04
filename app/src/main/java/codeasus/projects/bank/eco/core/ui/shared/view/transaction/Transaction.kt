package codeasus.projects.bank.eco.core.ui.shared.view.transaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import codeasus.projects.bank.eco.core.ui.shared.view.Profile
import codeasus.projects.bank.eco.core.ui.shared.view.utils.DataSourceDefaults
import codeasus.projects.bank.eco.core.ui.shared.view.utils.defineTransactionAmountColor
import codeasus.projects.bank.eco.core.ui.shared.view.utils.formatLocalDateTime
import codeasus.projects.bank.eco.core.ui.shared.view.utils.formatTransactionRate
import codeasus.projects.bank.eco.core.ui.shared.view.utils.formatUITransactionAmount
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme
import codeasus.projects.bank.eco.domain.local.model.customer.CustomerModel
import codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel

@Composable
fun Transaction(customerTransactionPair: Pair<CustomerModel, TransactionModel>) {
    ListItem(
        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
        leadingContent = {
            Profile(
                imageModifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                customer = customerTransactionPair.first
            ) {}
        },
        headlineContent = {
            Text(
                modifier = Modifier.padding(bottom = 4.dp),
                text = customerTransactionPair.first.name,
                style = TextStyle(fontWeight = FontWeight.Bold)
            )
        },
        supportingContent = {
            Text(
                text = formatLocalDateTime(customerTransactionPair.second.updatedAt),
            )
        },
        trailingContent = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text(
                        modifier = Modifier
                            .padding(bottom = 4.dp)
                            .align(Alignment.End),
                        text = formatUITransactionAmount(customerTransactionPair.second),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            color = defineTransactionAmountColor(customerTransactionPair.second.type)
                        )
                    )
                    Text(
                        modifier = Modifier.align(Alignment.End),
                        text = "Rate ${formatTransactionRate(customerTransactionPair.second.rate)}%"
                    )
                }
                Spacer(Modifier.width(5.dp))
                TransactionStatusIndicator(customerTransactionPair.second.status)
            }
        }
    )
}

@Preview(showSystemUi = false, showBackground = false)
@Composable
fun TransactionPreview() {
    EcoTheme {
        Transaction(
            Pair(
                DataSourceDefaults.getCustomers()[1],
                DataSourceDefaults.getTransactions()[1]
            )
        )
    }
}