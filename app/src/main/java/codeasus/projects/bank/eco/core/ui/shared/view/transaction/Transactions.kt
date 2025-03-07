package codeasus.projects.bank.eco.core.ui.shared.view.transaction

import android.content.res.Configuration
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import codeasus.projects.bank.eco.core.ui.shared.view.utils.DataSourceDefaults
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme
import codeasus.projects.bank.eco.domain.local.model.customer.CustomerModel
import codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel

@Composable
fun Transactions(customerTransactionPairs: List<Pair<CustomerModel, TransactionModel>>) {
    LazyColumn {
        items(
            count = customerTransactionPairs.size,
            contentType = { i -> customerTransactionPairs[i] }
        ) { index ->
            Transaction(customerTransactionPairs[index])
        }
    }
}

@Preview(
    showSystemUi = false,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true
)
@Composable
fun TransactionsPreview() {
    EcoTheme {
        Transactions(DataSourceDefaults.getCustomers().zip(DataSourceDefaults.getTransactions()))
    }
}