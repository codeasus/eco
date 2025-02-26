package codeasus.projects.bank.eco.core.navigation.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import codeasus.projects.bank.eco.R
import codeasus.projects.bank.eco.core.navigation.AppNavigator
import codeasus.projects.bank.eco.core.navigation.Screen.BottomNavbarScreen

data class BottomNavBarItem(
    val screen: BottomNavbarScreen,
    @DrawableRes val icon: Int
)

object BottomNavBar {
    val items = mapOf(
        Pair(
            BottomNavbarScreen.Home.route,
            BottomNavBarItem(screen = BottomNavbarScreen.Home, icon = R.drawable.ic_home)
        ),
        Pair(
            BottomNavbarScreen.Product.route,
            BottomNavBarItem(screen = BottomNavbarScreen.Product, icon = R.drawable.ic_product)
        ),
        Pair(
            BottomNavbarScreen.Transfer.route,
            BottomNavBarItem(screen = BottomNavbarScreen.Transfer, icon = R.drawable.ic_transfer)
        ),
        Pair(
            BottomNavbarScreen.Info.route,
            BottomNavBarItem(screen = BottomNavbarScreen.Info, icon = R.drawable.ic_info)
        ),
        Pair(
            BottomNavbarScreen.Reward.route,
            BottomNavBarItem(screen = BottomNavbarScreen.Reward, icon = R.drawable.ic_reward)
        )
    )
}

private fun getSelectedItemIndex(route: String?): BottomNavbarScreen {
    return BottomNavBar.items[route]?.screen ?: BottomNavbarScreen.Home
}

@Composable
fun BottomNavBar(navigator: AppNavigator) {
    val route = navigator.currentRoute

    NavigationBar(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)),
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) {
        BottomNavBar.items.values.forEachIndexed { _, item ->
            NavigationBarItem(
                selected = getSelectedItemIndex(route) == item.screen,
                onClick = {
                    if (getSelectedItemIndex(route) == item.screen) return@NavigationBarItem
                    navigator.navigateTo(item.screen)
                },
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (getSelectedItemIndex(route) == item.screen) item.screen.navItemName else "",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (getSelectedItemIndex(route) == item.screen) {
                                MaterialTheme.colorScheme.primary
                            } else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(item.icon),
                            tint = if (getSelectedItemIndex(route) == item.screen) {
                                MaterialTheme.colorScheme.primary
                            } else MaterialTheme.colorScheme.onSurfaceVariant,
                            contentDescription = item.screen.title
                        )
                    }
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
            )
        }
    }
}