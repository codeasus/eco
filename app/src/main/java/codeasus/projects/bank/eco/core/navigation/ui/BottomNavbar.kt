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

object BottomNavBarScreens {
    val items = mapOf(
        Pair(BottomNavbarScreen.Home.route, BottomNavbarScreen.Home),
        Pair(BottomNavbarScreen.Transfer.route, BottomNavbarScreen.Transfer),
        Pair(BottomNavbarScreen.Product.route, BottomNavbarScreen.Product)
    )
}

private fun getSelectedItemIndex(route: String?): BottomNavbarScreen {
    return BottomNavBarScreens.items[route] ?: BottomNavbarScreen.Home
}

@Composable
fun BottomNavbar(navigator: NavigationManager) {
    val currentScreen = getSelectedItemIndex(navigator.currentRoute)
    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        BottomNavBarScreens.items.values.forEachIndexed { _, screen ->
            NavigationBarItem(
                selected = currentScreen == screen,
                onClick = {
                    if (currentScreen == screen) return@NavigationBarItem
                    navigator.navigateTo(screen)
                },
                icon = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(screen.icon),
                        tint = if (currentScreen == screen) {
                            MaterialTheme.colorScheme.primary
                        } else MaterialTheme.colorScheme.onSurface,
                        contentDescription = screen.title
                    )
                },
                label = {
                    Text(
                        screen.title,
                        color = if (currentScreen == screen) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
            )
        }
    }
}