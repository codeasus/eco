package codeasus.projects.bank.eco.core.navigation.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import codeasus.projects.bank.eco.core.navigation.NavigationManager
import codeasus.projects.bank.eco.core.navigation.Screen.BottomNavbarScreen

data class BottomNavBarItem(
    val screen: BottomNavbarScreen,
)

object BottomNavBarScreens {
    val items = mapOf(
        Pair(
            BottomNavbarScreen.Home.route,
            BottomNavBarItem(screen = BottomNavbarScreen.Home)
        ),
        Pair(
            BottomNavbarScreen.Transfer.route,
            BottomNavBarItem(screen = BottomNavbarScreen.Transfer)
        ),
        Pair(
            BottomNavbarScreen.Product.route,
            BottomNavBarItem(screen = BottomNavbarScreen.Product)
        )
    )
}

private fun getSelectedItemIndex(route: String?): BottomNavbarScreen {
    return BottomNavBarScreens.items[route]?.screen ?: BottomNavbarScreen.Home
}

@Composable
fun BottomNavbar(navigator: NavigationManager) {
    val route = navigator.currentRoute

    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        BottomNavBarScreens.items.values.forEachIndexed { _, item ->
            NavigationBarItem(
                selected = getSelectedItemIndex(route) == item.screen,
                onClick = {
                    if (getSelectedItemIndex(route) == item.screen) return@NavigationBarItem
                    navigator.navigateTo(item.screen)
                },
                icon = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(item.screen.icon),
                        tint = if (getSelectedItemIndex(route) == item.screen) {
                            MaterialTheme.colorScheme.primary
                        } else MaterialTheme.colorScheme.onSurface,
                        contentDescription = item.screen.title
                    )
                },
                label = {
                    Text(
                        item.screen.title,
                        color = if (getSelectedItemIndex(route) == item.screen) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
            )
        }
    }
}