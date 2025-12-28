package codeasus.projects.bank.eco.feature.product.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import codeasus.projects.bank.eco.core.navigation.BottomNavbarScreen
import codeasus.projects.bank.eco.core.navigation.NavigationManager
import codeasus.projects.bank.eco.core.navigation.ui.BaseTopNavbar
import codeasus.projects.bank.eco.core.navigation.ui.BottomNavbar
import codeasus.projects.bank.eco.core.ui.shared.view.base.BaseScaffold
import codeasus.projects.bank.eco.core.ui.shared.view.base.MainBaseScreen
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme
import codeasus.projects.bank.eco.feature.product.presentation.states.ProductIntent
import codeasus.projects.bank.eco.feature.product.presentation.states.ProductState

@Composable
fun ProductScreenRoot(navigationManager: NavigationManager) {
    MainBaseScreen<ProductViewModel>(navigationManager) { navigationManager, vm ->
        val state = vm.state.collectAsStateWithLifecycle()

        ProductScreen(
            navigationManager = navigationManager,
            state = state.value,
            onAction = vm::handleIntent
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    navigationManager: NavigationManager,
    state: ProductState,
    onAction: (ProductIntent) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    BaseScaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            BaseTopNavbar(
                navigationManager = navigationManager,
                scrollBehavior = scrollBehavior,
                title = BottomNavbarScreen.Product.title,
                user = state.user,
                color = null
            )
        },
        bottomBar = {
            BottomNavbar(navigationManager)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

        }
    }
}

@Preview(showSystemUi = true, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ProductScreenLightPreview() {
    EcoTheme {
        ProductScreen(
            navigationManager = NavigationManager(rememberNavController()),
            state = ProductState(isLoading = false),
            onAction = {}
        )
    }
}

@Preview(showSystemUi = true, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ProductScreenDarkPreview() {
    EcoTheme {
        ProductScreen(
            navigationManager = NavigationManager(rememberNavController()),
            state = ProductState(isLoading = false),
            onAction = {}
        )
    }
}