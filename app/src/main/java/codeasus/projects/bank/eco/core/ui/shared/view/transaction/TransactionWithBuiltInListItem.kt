package codeasus.projects.bank.eco.core.ui.shared.view.transaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
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
import codeasus.projects.bank.eco.core.ui.shared.view.ImgProfile
import codeasus.projects.bank.eco.core.ui.shared.view.Profile
import codeasus.projects.bank.eco.core.ui.shared.view.models.CustomerUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.TransactionUi
import codeasus.projects.bank.eco.core.ui.shared.view.utils.DataSourceDefaults
import codeasus.projects.bank.eco.core.ui.shared.view.utils.defineTransactionAmountColor
import codeasus.projects.bank.eco.core.ui.shared.view.utils.formatTransactionRate
import codeasus.projects.bank.eco.core.ui.shared.view.utils.formatUITransactionAmount
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme

@Composable
fun TransactionWithBuiltInListItem(customerTransactionPair: Pair<CustomerUi, TransactionUi>) {
    ListItem(
        modifier = Modifier.padding(vertical = 0.dp),
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
            Text(text = customerTransactionPair.second.updatedAt)
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

@Composable
fun Transaction(customerTransactionPair: Pair<CustomerUi, TransactionUi>) {
    Surface(
        modifier = Modifier.height(64.dp),
        color = Color.Transparent
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ImgProfile(
                imageModifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                profileImageResId = customerTransactionPair.first.profileImg
            )

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1.0f),
                verticalArrangement = Arrangement.Center
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
                    text = "Rate ${formatTransactionRate(customerTransactionPair.second.rate)}%",
                    style = TextStyle(color = LocalContentColor.current.copy(alpha = 0.4f)),
                )
            }
            TransactionStatusIndicator(customerTransactionPair.second.status)
        }
    }
}

@Preview(showSystemUi = false, showBackground = false)
@Composable
fun TransactionPreview() {
    EcoTheme {
        TransactionWithBuiltInListItem(
            Pair(
                DataSourceDefaults.getCustomers()[1],
                DataSourceDefaults.getTransactions()[1]
            )
        )
    }
}

@Preview(showSystemUi = false, showBackground = false)
@Composable
fun Transaction_Preview() {
    EcoTheme {
        Transaction(
            Pair(
                DataSourceDefaults.getCustomers()[1],
                DataSourceDefaults.getTransactions()[1]
            )
        )
    }
}