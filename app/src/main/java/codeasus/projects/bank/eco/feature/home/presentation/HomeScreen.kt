package codeasus.projects.bank.eco.feature.home.presentation

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import codeasus.projects.bank.eco.R
import codeasus.projects.bank.eco.core.navigation.BottomNavbarScreen
import codeasus.projects.bank.eco.core.navigation.Card
import codeasus.projects.bank.eco.core.navigation.NavigationManager
import codeasus.projects.bank.eco.core.navigation.SearchTransaction
import codeasus.projects.bank.eco.core.navigation.ui.BaseTopNavbar
import codeasus.projects.bank.eco.core.navigation.ui.BottomNavbar
import codeasus.projects.bank.eco.core.ui.shared.view.TextButton
import codeasus.projects.bank.eco.core.ui.shared.view.base.BaseScaffold
import codeasus.projects.bank.eco.core.ui.shared.view.base.MainBaseScreen
import codeasus.projects.bank.eco.core.ui.shared.view.card.BankCardUnknown
import codeasus.projects.bank.eco.core.ui.shared.view.card.Cards
import codeasus.projects.bank.eco.core.ui.shared.view.states.BankAccountUiState
import codeasus.projects.bank.eco.core.ui.shared.view.transaction.LimitedTransactions
import codeasus.projects.bank.eco.core.ui.shared.view.utils.DataSourceDefaults
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme
import codeasus.projects.bank.eco.feature.card.presentation.utils.CardInstantAction
import codeasus.projects.bank.eco.feature.home.presentation.states.HomeIntent
import codeasus.projects.bank.eco.feature.home.presentation.states.HomeState

@Composable
fun HomeScreenRoot(navigationManager: NavigationManager) {
    MainBaseScreen<HomeViewModel>(navigationManager) { navigationManager, vm ->
        val state = vm.state.collectAsStateWithLifecycle()

        HomeScreen(
            navigationManager = navigationManager,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigationManager: NavigationManager,
    state: HomeState,
    onAction: (HomeIntent) -> Unit,
    onNavigateToCardScreen: (String) -> Unit = {},
    onNavigateToSearchTransactionScreen: () -> Unit = {}
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val scrollState = rememberScrollState()

    BaseScaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            BaseTopNavbar(
                navigationManager = navigationManager,
                scrollBehavior = scrollBehavior,
                title = BottomNavbarScreen.Home.title,
                user = state.user,
                color = darkerBankAccountContainerColor,
            )
        },
        bottomBar = {
            BottomNavbar(navigationManager)
        }
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(bottom = 16.dp).verticalScroll(scrollState), verticalArrangement = Arrangement.spacedBy(42.dp)) {
            Column(
                modifier = Modifier
                    .backgroundCards()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
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
                                bankAccountUiState = state.bankAccountsUiState
                            )
                        }
                    }

                    is BankAccountUiState.Success -> {
                        Cards(
                            userBankAccounts = state.bankAccountsUiState.data,
                            onSelected = { bankAccount -> onNavigateToCardScreen(bankAccount.id) },
                            onSwiped = { onAction(HomeIntent.RestackCards) }
                        )
                    }
                }
                Row(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CardInstantAction(R.drawable.ic_topup, "Top-up", state.bankAccountsUiState is BankAccountUiState.Success) {}
                    CardInstantAction(R.drawable.ic_transfer, "Transfer", state.bankAccountsUiState is BankAccountUiState.Success) {}
                    CardInstantAction(R.drawable.ic_location, "ATMs", true) {}
                }
            }

            TemplatePaymentsComponent()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4F))
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Transactions")
                    TextButton("View all") { onNavigateToSearchTransactionScreen() }
                }
                LimitedTransactions(state.transactions)
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun HomeScreenLightPreview() {
    EcoTheme {
        HomeScreen(
            navigationManager = NavigationManager(rememberNavController()),
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
            navigationManager = NavigationManager(rememberNavController()),
            state = HomeState(
                transactions = DataSourceDefaults.getCustomerTransactions(),
                bankAccountsUiState = BankAccountUiState.Success(DataSourceDefaults.unknownUser.second)
            ),
            onAction = {}
        )
    }
}