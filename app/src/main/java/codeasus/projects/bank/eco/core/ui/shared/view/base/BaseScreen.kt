package codeasus.projects.bank.eco.core.ui.shared.view.base

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import codeasus.projects.bank.eco.core.navigation.AppNavigator
import codeasus.projects.bank.eco.core.navigation.ui.BottomNavbar
import codeasus.projects.bank.eco.core.navigation.ui.TopNavbarRouter
import codeasus.projects.bank.eco.core.ui.shared.viewmodel.base.BaseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
inline fun <reified T : BaseViewModel> BaseScreen(navigator: AppNavigator, crossinline content: @Composable (T) -> Unit) {
    val viewModel: T = hiltViewModel()

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
        state = rememberTopAppBarState()
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { TopNavbarRouter(scrollBehavior = scrollBehavior, navigator = navigator) },
        containerColor = MaterialTheme.colorScheme.surface,
        bottomBar = { BottomNavbar(navigator = navigator) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content(viewModel)
        }
    }
}