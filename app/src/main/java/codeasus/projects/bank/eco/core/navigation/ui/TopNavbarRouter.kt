package codeasus.projects.bank.eco.core.navigation.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import codeasus.projects.bank.eco.core.navigation.AppNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavbarRouter(
    scrollBehavior: TopAppBarScrollBehavior,
    navigator: AppNavigator,
    onNotificationClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    TopNavbar(
        scrollBehavior = scrollBehavior,
        navigator = navigator,
        onNotificationClick = onNotificationClick,
        onProfileClick = onProfileClick
    )
}