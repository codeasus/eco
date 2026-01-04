package codeasus.projects.bank.eco.feature.search_transaction.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import codeasus.projects.bank.eco.core.navigation.NavigationManager
import codeasus.projects.bank.eco.core.navigation.SearchTransaction
import codeasus.projects.bank.eco.core.ui.shared.view.base.BaseScaffold
import codeasus.projects.bank.eco.core.ui.shared.view.base.BaseScreen
import codeasus.projects.bank.eco.core.ui.shared.view.transaction.Transactions
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme
import codeasus.projects.bank.eco.domain.local.model.enums.TransactionType
import codeasus.projects.bank.eco.feature.search_transaction.states.SearchTransactionIntent
import codeasus.projects.bank.eco.feature.search_transaction.states.SearchTransactionState
import codeasus.projects.bank.eco.feature.view_transaction.view.TransactionBottomSheet

@Composable
fun SearchTransactionScreenRoot(navigationManager: NavigationManager) {
    BaseScreen<SearchTransactionViewModel> { vm ->
        val state = vm.state.collectAsStateWithLifecycle()

        SearchTransactionScreen(state.value, vm::handleIntent) {
            navigationManager.navigateUp()
        }
    }
}

@Composable
fun SearchTransactionScreen(
    state: SearchTransactionState,
    onAction: (SearchTransactionIntent) -> Unit,
    onBackClick: () -> Unit
) {
    BaseScaffold(
        topBar = {
            SearchTopNavbar(
                title = SearchTransaction.title,
                searchTransactionState = state,
                onBackClick = onBackClick,
                onSearchClick = { onAction(SearchTransactionIntent.ToggleSearchTextVisibility) },
                onSearchTextValueChange = { onAction(SearchTransactionIntent.SetSearchText(it)) }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(TransactionType.entries) { transactionType ->
                    FilterChip(
                        onClick = { onAction(SearchTransactionIntent.SelectTransactionType(transactionType)) },
                        selected = state.selectedTransactionTypes[transactionType]?: false,
                        label = {
                            Text(text = transactionType.label)
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            labelColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            selectedLabelColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        ),
                        shape = RoundedCornerShape(18.dp)
                    )
                }
            }
            Transactions(state.transactions) { transactionId ->
                onAction(SearchTransactionIntent.ShowBottomSheet(transactionId))
            }
        }

        TransactionBottomSheet(transactionUiState = state.transactionUiState, isVisible = state.showBottomSheet) {
            onAction(SearchTransactionIntent.HideBottomSheet)
        }
    }
}

@Preview(
    showSystemUi = false,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun SearchScreenPreview() {
    EcoTheme {
        SearchTransactionScreen(
            state = SearchTransactionState(),
            onAction = {},
            onBackClick = {}
        )
    }
}