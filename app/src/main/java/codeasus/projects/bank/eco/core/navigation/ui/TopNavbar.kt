package codeasus.projects.bank.eco.core.navigation.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.TextStyle
import androidx.hilt.navigation.compose.hiltViewModel
import codeasus.projects.bank.eco.core.navigation.AppNavigator
import codeasus.projects.bank.eco.core.navigation.Screen
import codeasus.projects.bank.eco.core.ui.shared.view.utils.DataSourceDefaults
import codeasus.projects.bank.eco.core.ui.shared.viewmodel.user.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavbar(
    scrollBehavior: TopAppBarScrollBehavior,
    viewModel: UserViewModel = hiltViewModel(),
    navigator: AppNavigator,
    onNotificationClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    val user by viewModel.userState.collectAsState()
    val currentScreen = Screen.fromRoute(navigator.currentRoute)

    TopAppBar(
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        title = {
            Text(
                text = currentScreen.title,
                style = TextStyle(fontSize = MaterialTheme.typography.titleLarge.fontSize)
            )
        },
        actions = {
            NotificationAction { onNotificationClick() }
            ProfileAction(if (user != null) user!! else DataSourceDefaults.unknownUser) { onProfileClick() }
        }
    )
}
