package codeasus.projects.bank.eco.core.ui.shared.view.base

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import codeasus.projects.bank.eco.core.navigation.NavigationManager
import codeasus.projects.bank.eco.core.navigation.ui.BaseTopNavbar
import codeasus.projects.bank.eco.core.navigation.ui.BottomNavbar
import codeasus.projects.bank.eco.core.ui.shared.viewmodel.base.BaseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
inline fun <reified T : BaseViewModel> MainBaseScreen(navigationManager: NavigationManager, title: String, crossinline content: @Composable (T) -> Unit) {
    val viewModel: T = hiltViewModel()

    val userState = viewModel.user.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())


    BaseScaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            BaseTopNavbar(
                scrollBehavior = scrollBehavior,
                title = title,
                user = userState.value,
                onNotificationClick = {

                },
                onProfileClick = {

                }
            )
        },
        bottomBar = {
            BottomNavbar(navigationManager)
        }
    ) {
        content(viewModel)
    }
}