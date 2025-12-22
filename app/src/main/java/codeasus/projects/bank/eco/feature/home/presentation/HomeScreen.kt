package codeasus.projects.bank.eco.feature.home.presentation

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import codeasus.projects.bank.eco.core.navigation.BottomNavbarScreen
import codeasus.projects.bank.eco.core.navigation.Card
import codeasus.projects.bank.eco.core.navigation.NavigationManager
import codeasus.projects.bank.eco.core.navigation.SearchTransaction
import codeasus.projects.bank.eco.core.ui.shared.view.base.MainBaseScreen
import codeasus.projects.bank.eco.core.ui.shared.view.card.BankCardUnknown
import codeasus.projects.bank.eco.core.ui.shared.view.card.Cards
import codeasus.projects.bank.eco.core.ui.shared.view.states.BankAccountUiState
import codeasus.projects.bank.eco.core.ui.shared.view.transaction.Transactions
import codeasus.projects.bank.eco.core.ui.shared.view.transaction.TransactionsBasic
import codeasus.projects.bank.eco.core.ui.shared.view.utils.DataSourceDefaults
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme
import codeasus.projects.bank.eco.feature.home.presentation.states.HomeIntent
import codeasus.projects.bank.eco.feature.home.presentation.states.HomeState

@Composable
fun HomeScreenRoot(navigationManager: NavigationManager) {
    MainBaseScreen<HomeViewModel>(navigationManager, BottomNavbarScreen.Home.title) { vm ->
        val state = vm.state.collectAsStateWithLifecycle()

        HomeScreen(
            state = state.value, onAction = vm::handleIntent,
            onNavigateToCardScreen = {
                navigationManager.navigateTo(Card(it))
            },
            onNavigateToSearchTransactionScreen = {
                navigationManager.navigateTo(SearchTransaction)
            }
        )
    }
}

@Composable
fun HomeScreen(
    state: HomeState,
    onAction: (HomeIntent) -> Unit,
    onNavigateToCardScreen: (String) -> Unit = {},
    onNavigateToSearchTransactionScreen: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Start),
            text = "Cards",
            style = TextStyle(fontSize = MaterialTheme.typography.headlineLarge.fontSize)
        )
        when (state.bankAccountsUiState) {
            is BankAccountUiState.Idle -> {}
            is BankAccountUiState.NotFound -> {}
            is BankAccountUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    BankCardUnknown(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp),
                        state.bankAccountsUiState
                    )
                }
            }

            is BankAccountUiState.Success -> {
                Cards(
                    userBankAccounts = state.bankAccountsUiState.data,
                    onSelected = { bankAccount ->
                        onNavigateToCardScreen(bankAccount.id)
                    },
                    onSwiped = {
                        onAction(HomeIntent.RestackCards)
                    }
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Transactions",
                style = TextStyle(fontSize = MaterialTheme.typography.headlineLarge.fontSize)
            )
            Text(
                modifier = Modifier.clickable {
                    onNavigateToSearchTransactionScreen()
                },
                text = "View All",
                style = TextStyle(color = MaterialTheme.colorScheme.primary),
                textDecoration = TextDecoration.Underline
            )
        }
        Transactions(state.transactions)
    }
}

@Preview(showSystemUi = true, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun HomeScreenLightPreview() {
    EcoTheme {
        HomeScreen(
            state = HomeState(
                transactions = DataSourceDefaults.getCustomerTransactions(),
                bankAccountsUiState = BankAccountUiState.Success(DataSourceDefaults.unknownUser.second)
            ),
            onAction = {}
        )
    }
}

@Preview(showSystemUi = true, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenDarkPreview() {
    EcoTheme {
        HomeScreen(
            state = HomeState(
                transactions = DataSourceDefaults.getCustomerTransactions(),
                bankAccountsUiState = BankAccountUiState.Success(DataSourceDefaults.unknownUser.second)
            ),
            onAction = {}
        )
    }
}