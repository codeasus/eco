package codeasus.projects.bank.eco.feature.product.presentation

import android.content.res.Configuration
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import codeasus.projects.bank.eco.core.navigation.NavigationManager
import codeasus.projects.bank.eco.core.navigation.ui.BaseTopNavbar
import codeasus.projects.bank.eco.core.navigation.ui.BottomNavbar
import codeasus.projects.bank.eco.core.ui.shared.view.base.BaseScaffold
import codeasus.projects.bank.eco.core.ui.shared.view.base.BaseScreen
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(navigationManager: NavigationManager) {
    BaseScreen<ProductViewModel> { vm ->
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

        val userState = vm.user.collectAsStateWithLifecycle()

        BaseScaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                BaseTopNavbar(
                    scrollBehavior = scrollBehavior,
                    navigationManager = navigationManager,
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

        }
    }
}

@Preview(
    showSystemUi = false,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun ProductScreenPreview() {
    EcoTheme {
        val nav = NavigationManager(rememberNavController())
        ProductScreen(nav)
    }
}