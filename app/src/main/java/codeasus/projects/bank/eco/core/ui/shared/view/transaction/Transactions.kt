package codeasus.projects.bank.eco.core.ui.shared.view.transaction

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import codeasus.projects.bank.eco.core.ui.shared.view.utils.DataSourceDefaults
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme
import codeasus.projects.bank.eco.feature.home.presentation.states.TransactionListItemUI

@Composable
fun Transactions(
    transactions: List<TransactionListItemUI>,
    onTransactionSelected: (String) -> Unit
) {
    LazyColumn {
        items(
            count = transactions.size,
            key = {  transactions[it].hashCode() },
            contentType = { transactions[it] }
        ) { index ->
            val transaction = transactions[index]
            if (transaction is TransactionListItemUI.TransactionItem) {
                TransactionListItem(transaction.transactionWithCustomer, onTransactionSelected)
            } else if (transaction is TransactionListItemUI.TransactionDateItem) {
                TransactionDateListItem(transaction.date)
            }
        }
    }
}

@Composable
fun LimitedTransactionsWithDates(
    transactions: List<TransactionListItemUI>,
    onTransactionSelected: (String) -> Unit
) {
    Column {
        for (transaction in transactions.take(10)) {
            if (transaction is TransactionListItemUI.TransactionItem) {
                TransactionListItem(transaction.transactionWithCustomer, onTransactionSelected)
            } else if (transaction is TransactionListItemUI.TransactionDateItem) {
                TransactionDateListItem(transaction.date)
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
        LimitedTransactionsWithDates(DataSourceDefaults.getCustomerTransactions()) {}
    }
}