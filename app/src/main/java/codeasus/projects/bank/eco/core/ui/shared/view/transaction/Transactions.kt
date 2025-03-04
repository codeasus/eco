package codeasus.projects.bank.eco.core.ui.shared.view.transaction

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import codeasus.projects.bank.eco.core.ui.shared.view.utils.DataSourceDefaults
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme
import codeasus.projects.bank.eco.domain.local.model.customer.CustomerModel
import codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel

@Composable
fun Transactions(customerTransactionPairs: List<Pair<CustomerModel, TransactionModel>>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topEnd = 24.dp, topStart = 24.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(top = 12.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        LazyColumn {
            items(
                count = customerTransactionPairs.size,
                contentType = { i -> customerTransactionPairs[i] }
            ) { index ->
                Transaction(customerTransactionPairs[index])
            }
        }
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