package codeasus.projects.bank.eco.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import codeasus.projects.bank.eco.feature.home.presentation.HomeScreen
import codeasus.projects.bank.eco.feature.product.presentation.ProductScreen
import codeasus.projects.bank.eco.feature.search_transaction.presentation.SearchTransactionScreen
import codeasus.projects.bank.eco.feature.transfer.presentation.TransferScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    val navigationManager = remember(navController) { NavigationManager(navController) }

    NavHost(navController = navController, startDestination = BottomNavbarScreen.Home) {
        composable<BottomNavbarScreen.Home> {
            HomeScreen(navigationManager = navigationManager)
        }
        composable<BottomNavbarScreen.Product> {
            ProductScreen(navigationManager = navigationManager)
        }
        composable<BottomNavbarScreen.Transfer> {
            TransferScreen(navigationManager = navigationManager)
        }
        composable<SearchTransaction> {
            SearchTransactionScreen(navigationManager = navigationManager)
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