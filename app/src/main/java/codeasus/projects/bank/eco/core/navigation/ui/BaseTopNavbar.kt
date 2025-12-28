package codeasus.projects.bank.eco.core.navigation.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import codeasus.projects.bank.eco.core.navigation.NavigationManager
import codeasus.projects.bank.eco.core.navigation.Profile
import codeasus.projects.bank.eco.core.navigation.SystemMessages
import codeasus.projects.bank.eco.core.ui.shared.view.models.UserUi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseTopNavbar(navigationManager: NavigationManager, scrollBehavior: TopAppBarScrollBehavior, title: String, user: UserUi, color: Color?) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(containerColor = color ?: MaterialTheme.colorScheme.surface),
        title = {
            Text(
                text = title,
                style = TextStyle(fontSize = MaterialTheme.typography.titleLarge.fontSize)
            )
        },
        actions = {
            NotificationAction { navigationManager.navigateTo(SystemMessages) }
            ProfileAction(user) { navigationManager.navigateTo(Profile) }
        }
    )
}
