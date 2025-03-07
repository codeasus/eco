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
import androidx.navigation.compose.rememberNavController
import codeasus.projects.bank.eco.core.navigation.NavigationManager
import codeasus.projects.bank.eco.core.navigation.Screen
import codeasus.projects.bank.eco.core.navigation.ui.SearchTopNavbar
import codeasus.projects.bank.eco.core.ui.shared.view.base.BaseScaffold
import codeasus.projects.bank.eco.core.ui.shared.view.base.BaseScreen
import codeasus.projects.bank.eco.core.ui.shared.view.transaction.Transactions
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme

@Composable
fun SearchTransactionScreen(navigationManager: NavigationManager) {
    BaseScreen<SearchTransactionViewModel> { vm ->
        val currentScreen = Screen.fromRoute(navigationManager.currentRoute)
        val uiSelectedTransactionTypeState =
            vm.uiSelectedTransactionTypeState.collectAsStateWithLifecycle()
        val uiSearchState = vm.uiSearchState.collectAsStateWithLifecycle()
        val transactions = vm.transactions.collectAsStateWithLifecycle()
        BaseScaffold(
            topBar = {
                SearchTopNavbar(
                    title = currentScreen.title,
                    uiSearchState = uiSearchState.value,
                    onBackClick = { navigationManager.navigateUp() },
                    onSearchClick = { vm.toggleSearchTextVisibility() },
                    onSearchTextValueChange = { vm.setSearchText(it) },
                    onSearch = { vm.searchActionEngaged() }
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
                    items(uiSelectedTransactionTypeState.value.keys.toList()) { transactionType ->
                        FilterChip(
                            onClick = { vm.setTransactionType(transactionType) },
                            selected = vm.getTransactionTypeSelectState(transactionType),
                            label = {
                                Text(text = transactionType.name)
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
                Transactions(transactions.value)
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
fun SearchScreenPreview() {
    EcoTheme {
        val nav = NavigationManager(rememberNavController())
        SearchTransactionScreen(nav)
    }
}