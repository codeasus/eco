package codeasus.projects.bank.eco.core.navigation.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import codeasus.projects.bank.eco.core.navigation.AppNavigator
import codeasus.projects.bank.eco.core.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmartTopNavbar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    navigator: AppNavigator,
    onSearchClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    val currentScreen = Screen.fromRoute(navigator.currentRoute)
    if (navigator.isHomeScreen()) {
        HomeTopNavbar(
            modifier = modifier,
            scrollBehavior = scrollBehavior,
            onSearchClick = onSearchClick,
            onNotificationClick = onNotificationClick,
            onProfileClick = onProfileClick
        )
    } else {
        GenericTopNavbar(
            modifier = modifier,
            scrollBehavior = scrollBehavior,
            title = currentScreen.title,
            onBackClick = { navigator.navigateUp() },
            onSearchClick = onSearchClick,
            onNotificationClick = onNotificationClick,
            onProfileClick = onProfileClick
        )
    }
}
