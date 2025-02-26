package codeasus.projects.bank.eco.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import codeasus.projects.bank.eco.feature.home.presentation.HomeScreen
import codeasus.projects.bank.eco.feature.info.presentation.InfoScreen
import codeasus.projects.bank.eco.feature.product.presentation.ProductScreen
import codeasus.projects.bank.eco.feature.reward.presentation.RewardScreen
import codeasus.projects.bank.eco.feature.subgraphone.presentation.SubgraphOneScreen
import codeasus.projects.bank.eco.feature.subgraphtwo.presentation.SubgraphTwoScreen
import codeasus.projects.bank.eco.feature.transfer.presentation.TransferScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: Screen = Screen.Home
) {
    val appNavigator = remember(navController) {
        AppNavigator(navController)
    }

    NavHost(
        navController = navController,
        startDestination = startDestination.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navigator = appNavigator)
        }
        composable(Screen.Product.route) {
            ProductScreen(navigator = appNavigator)
        }
        composable(Screen.Transfer.route) {
            TransferScreen(navigator = appNavigator)
        }
        composable(Screen.Info.route) {
            InfoScreen(navigator = appNavigator)
        }
        composable(Screen.Reward.route) {
            RewardScreen(navigator = appNavigator)
        }
        composable(Screen.SubgraphOne.route) {
            SubgraphOneScreen(navigator = appNavigator)
        }
        composable(Screen.SubgraphTwo.route) {
            SubgraphTwoScreen(navigator = appNavigator)
        }
    }
}

class AppNavigator(private val navController: NavHostController) {
    val currentRoute: String?
        get() = navController.currentDestination?.route

    fun navigateTo(screen: Screen) {
        navController.navigate(screen.route)
    }

    fun navigateUp() {
        navController.navigateUp()
    }

    fun isHomeScreen(): Boolean = currentRoute == Screen.Home.route
}