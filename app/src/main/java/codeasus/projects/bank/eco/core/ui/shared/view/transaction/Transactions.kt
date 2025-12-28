package codeasus.projects.bank.eco.core.ui.shared.view.transaction

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import codeasus.projects.bank.eco.core.ui.shared.view.models.CustomerUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.TransactionUi
import codeasus.projects.bank.eco.core.ui.shared.view.utils.DataSourceDefaults
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme

@Composable
fun Transactions(customerTransactionPairs: List<Pair<CustomerUi, TransactionUi>>) {
    LazyColumn {
        items(
            count = customerTransactionPairs.size,
            contentType = { i -> customerTransactionPairs[i] }
        ) { index ->
            TransactionListItem(customerTransactionPairs[index])
        }
    }
}

@Composable
fun LimitedTransactions(customerTransactionPairs: List<Pair<CustomerUi, TransactionUi>>) {
    Column {
        for (customerTransactionPair in customerTransactionPairs.take(10)) {
            TransactionListItem(customerTransactionPair)
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
        LimitedTransactions(DataSourceDefaults.getCustomers().zip(DataSourceDefaults.getTransactions()))
    }
}