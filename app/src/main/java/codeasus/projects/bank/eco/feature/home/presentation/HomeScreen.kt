package codeasus.projects.bank.eco.feature.home.presentation

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import codeasus.projects.bank.eco.core.navigation.NavigationManager
import codeasus.projects.bank.eco.core.navigation.Screen
import codeasus.projects.bank.eco.core.navigation.ui.BaseTopNavbar
import codeasus.projects.bank.eco.core.navigation.ui.BottomNavbar
import codeasus.projects.bank.eco.core.ui.shared.view.base.BaseScaffold
import codeasus.projects.bank.eco.core.ui.shared.view.base.BaseScreen
import codeasus.projects.bank.eco.core.ui.shared.view.card.Cards
import codeasus.projects.bank.eco.core.ui.shared.view.transaction.Transactions
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navigationManager: NavigationManager) {
    BaseScreen<HomeViewModel> { vm ->
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

        val userState = vm.user.collectAsStateWithLifecycle()
        val bankCars = vm.bankCards.collectAsStateWithLifecycle()
        val transactions = vm.transactions.collectAsStateWithLifecycle()

        BaseScaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                BaseTopNavbar(
                    scrollBehavior = scrollBehavior,
                    navigationManager = navigationManager,
                    user = userState.value,
                    onNotificationClick = {

                    },
                    onProfileClick = {

                    }
                )
            },
            bottomBar = {
                BottomNavbar(navigationManager)
            }
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
                Cards(
                    userBankAccounts = bankCars.value,
                    onCardSelected = {

                    },
                    onCardSwiped = {
                        vm.reStackCards()
                    }
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(onClick = {}) {
                        Text(text = "Top Up")
                    }
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        ),
                        onClick = {}) {
                        Text(text = "Send")
                    }

                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        ),
                        onClick = {}) {
                        Text(text = "Request")
                    }
                }
                Spacer(Modifier.height(16.dp))
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
                            navigationManager.navigateTo(Screen.SearchTransaction)
                        },
                        text = "View All",
                        style = TextStyle(color = MaterialTheme.colorScheme.secondary),
                        textDecoration = TextDecoration.Underline
                    )
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
fun HomeScreenPreview() {
    EcoTheme {
        val nav = NavigationManager(rememberNavController())
        HomeScreen(nav)
    }
}