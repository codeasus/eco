package codeasus.projects.bank.eco.core.navigation

import androidx.annotation.DrawableRes
import codeasus.projects.bank.eco.R

sealed class Screen(val route: String, val title: String) {
    data object Home : Screen("home", "Home")
    data object Transfer : Screen("transfer", "Transfer")
    data object Product : Screen("product", "Product")
    data object SearchTransaction : Screen("search_transaction", "Transactions")

    sealed class BottomNavbarScreen(screen: Screen, val navItemName: String, @DrawableRes val icon: Int) : Screen(screen.route, screen.title) {
        data object Home : BottomNavbarScreen(Screen.Home, "Home",  R.drawable.ic_home)
        data object Product : BottomNavbarScreen(Screen.Product, "Product",  R.drawable.ic_product)
        data object Transfer : BottomNavbarScreen(Screen.Transfer, "Transfer",  R.drawable.ic_transfer)
    }

    companion object {
        fun fromRoute(route: String?): Screen {
            return when (route) {
                Home.route -> BottomNavbarScreen.Home
                Product.route -> BottomNavbarScreen.Product
                Transfer.route -> BottomNavbarScreen.Transfer
                SearchTransaction.route -> SearchTransaction
                else -> Home
            }
        }
    }
}
