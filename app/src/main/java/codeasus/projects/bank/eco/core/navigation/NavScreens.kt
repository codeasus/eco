package codeasus.projects.bank.eco.core.navigation


sealed class Screen(val route: String, val title: String) {
    data object Home : Screen("home", "Eco Ltd.")
    data object Transfer : Screen("transfer", "Money Transfer")
    data object SubgraphOne : Screen("subgraph_one", "SubgraphOne Screen")
    data object SubgraphTwo : Screen("subgraph_two", "SubgraphTwo Screen")
    data object Product : Screen("product", "Product")
    data object Info : Screen("info", "Info")
    data object Reward : Screen("reward", "Rewards")

    sealed class BottomNavbarScreen(screen: Screen, val navItemName: String) :
        Screen(screen.route, screen.title) {
        data object Home : BottomNavbarScreen(Screen.Home, "Home")
        data object Product : BottomNavbarScreen(Screen.Product, "Product")
        data object Transfer : BottomNavbarScreen(Screen.Transfer, "Transfer")
        data object Info : BottomNavbarScreen(Screen.Info, "Info")
        data object Reward : BottomNavbarScreen(Screen.Reward, "Rewards")
    }

    companion object {
        fun fromRoute(route: String?): Screen {
            return when (route) {
                Home.route -> Home
                Product.route -> Product
                Transfer.route -> Transfer
                Info.route -> Info
                Reward.route -> Reward
                SubgraphOne.route -> SubgraphOne
                SubgraphTwo.route -> SubgraphTwo
                else -> Home
            }
        }
    }
}
