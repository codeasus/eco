package codeasus.projects.bank.eco.core.ui.shared.view.transaction

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import codeasus.projects.bank.eco.R
import codeasus.projects.bank.eco.core.ui.shared.view.utils.DataSourceDefaults
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme
import codeasus.projects.bank.eco.domain.local.model.customer.CustomerModel

@Composable
fun Transactions(customerTransactionPairs: List<Pair<CustomerModel, codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel>>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(modifier = Modifier, text = "Recent Transactions")
        LazyColumn(modifier = Modifier.fillMaxHeight(0.85f)) {
            items(
                count = customerTransactionPairs.size,
                contentType = { i -> customerTransactionPairs[i] }
            ) { index ->
                Transaction(customerTransactionPairs[index])
            }
        }
        Icon(
            modifier = Modifier
                .height(24.dp)
                .width(42.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(
                        0.4F
                    )
                ),
            painter = painterResource(R.drawable.ic_dots),
            contentDescription = "Expand",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(
    showSystemUi = false,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun TransactionsPreview() {
    EcoTheme {
        Transactions(DataSourceDefaults.getCustomers().zip(DataSourceDefaults.getTransactions()))
    }
}