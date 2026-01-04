package codeasus.projects.bank.eco.core.ui.shared.view.transaction

import android.content.res.Configuration
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import codeasus.projects.bank.eco.core.ui.shared.view.models.CustomerUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.TransactionUi
import codeasus.projects.bank.eco.core.ui.shared.view.utils.DataSourceDefaults
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme

@Composable
fun TransactionsBasic(customerTransactionPairs: List<Pair<CustomerUi, TransactionUi>>) {
    LazyColumn {
        items(
            count = customerTransactionPairs.size,
            contentType = { i -> customerTransactionPairs[i] }
        ) { index ->
            TransactionListItemBasic(customerTransactionPairs[index])
        }
    }
}

@Preview(
    showSystemUi = false,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun TransactionsBasicPreview() {
    EcoTheme {
        Transactions(DataSourceDefaults.getCustomers().zip(DataSourceDefaults.getTransactions())) {}
    }
}