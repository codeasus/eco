package codeasus.projects.bank.eco.core.navigation.ui

import android.util.Log
import androidx.annotation.DrawableRes
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
import androidx.navigation.NavController
import codeasus.projects.bank.eco.R
import codeasus.projects.bank.eco.core.navigation.BottomNavbarScreen
import codeasus.projects.bank.eco.core.navigation.NavigationManager
import kotlin.reflect.KClass

data class BottomNavbarScreen<T : Any>(val route: T, @DrawableRes val icon: Int)

object BottomNavBarScreens {
    val items = arrayOf(
        BottomNavbarScreen(BottomNavbarScreen.Home, R.drawable.ic_home),
        BottomNavbarScreen(BottomNavbarScreen.Transfer, R.drawable.ic_transfer),
        BottomNavbarScreen(BottomNavbarScreen.Product, R.drawable.ic_product),
    )
}

private fun NavController.isCurrentScreen(screenClass: KClass<out BottomNavbarScreen>): Boolean {
    val route = this.currentBackStackEntry?.destination?.route?: return false
    Log.d("Navigation-route: ", route)
    Log.d("Navigation-class: ", screenClass.qualifiedName.toString())
    return route.startsWith(screenClass.qualifiedName.toString())
}

@Composable
fun BottomNavbar(navigator: NavigationManager) {
    val nav = navigator.navController
    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        BottomNavBarScreens.items.forEach { navItem ->
            NavigationBarItem(
                selected = nav.isCurrentScreen(navItem.route::class),
                onClick = {
                    if (nav.isCurrentScreen(navItem.route::class)) return@NavigationBarItem
                    nav.navigate(navItem.route) {
                        popUpTo(nav.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(navItem.icon),
                        tint = if (nav.isCurrentScreen(navItem.route::class)) { MaterialTheme.colorScheme.primary } else MaterialTheme.colorScheme.onSurface,
                        contentDescription = navItem.route.title
                    )
                },
                label = {
                    Text(
                        navItem.route.title,
                        color = if (nav.isCurrentScreen(navItem.route::class)) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
            )
        }
    }
}