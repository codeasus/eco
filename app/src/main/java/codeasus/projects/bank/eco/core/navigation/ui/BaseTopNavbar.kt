package codeasus.projects.bank.eco.core.navigation.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import codeasus.projects.bank.eco.core.navigation.NavigationManager
import codeasus.projects.bank.eco.core.navigation.Screen
import codeasus.projects.bank.eco.core.ui.shared.view.utils.DataSourceDefaults
import codeasus.projects.bank.eco.domain.local.model.user.UserModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseTopNavbar(
    scrollBehavior: TopAppBarScrollBehavior,
    user: UserModel?,
    navigationManager: NavigationManager,
    onNotificationClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    val currentScreen = Screen.fromRoute(navigationManager.currentRoute)

    TopAppBar(
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface),
        title = {
            Text(
                text = currentScreen.title,
                style = TextStyle(fontSize = MaterialTheme.typography.titleLarge.fontSize)
            )
        },
        actions = {
            NotificationAction { onNotificationClick() }
            ProfileAction(user ?: DataSourceDefaults.unknownUser) { onProfileClick() }
        }
    )
}
