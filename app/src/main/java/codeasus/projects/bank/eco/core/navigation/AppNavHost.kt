package codeasus.projects.bank.eco.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import codeasus.projects.bank.eco.feature.card.presentation.CardScreenRoot
import codeasus.projects.bank.eco.feature.home.presentation.HomeScreenRoot
import codeasus.projects.bank.eco.feature.product.presentation.ProductScreenRoot
import codeasus.projects.bank.eco.feature.profile.presentation.ProfileScreenRoot
import codeasus.projects.bank.eco.feature.search_transaction.presentation.SearchTransactionScreenRoot
import codeasus.projects.bank.eco.feature.system_messages.presentation.SystemMessagesScreenRoot
import codeasus.projects.bank.eco.feature.transfer.presentation.TransferScreenRoot

@Composable
fun AppNavHost(navController: NavHostController) {
    val navigationManager = remember(navController) { NavigationManager(navController) }

    NavHost(navController = navController, startDestination = BottomNavbarScreen.Home) {
        composable<BottomNavbarScreen.Home> {
            HomeScreenRoot(navigationManager = navigationManager)
        }
        composable<Card> {
            val args = it.toRoute<Card>()
            CardScreenRoot(navigationManager = navigationManager, args.bankAccountId)
        }
        composable<BottomNavbarScreen.Product> {
            ProductScreenRoot(navigationManager = navigationManager)
        }
        composable<BottomNavbarScreen.Transfer> {
            TransferScreenRoot(navigationManager = navigationManager)
        }
        composable<SearchTransaction> {
            SearchTransactionScreenRoot(navigationManager = navigationManager)
        }
        composable<SystemMessages> {
            SystemMessagesScreenRoot(navigationManager = navigationManager)
        }
        composable<Profile> {
            ProfileScreenRoot(navigationManager = navigationManager)
        }
    }
}

class NavigationManager(val navController: NavHostController) {
    fun navigateTo(screen: Screen) {
        navController.navigate(screen)
    }

    fun navigateUp() {
        navController.navigateUp()
    }
}