package codeasus.projects.bank.eco.feature.product.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import codeasus.projects.bank.eco.core.navigation.BottomNavbarScreen
import codeasus.projects.bank.eco.core.navigation.NavigationManager
import codeasus.projects.bank.eco.core.ui.shared.view.base.MainBaseScreen
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme
import codeasus.projects.bank.eco.feature.product.presentation.states.ProductIntent
import codeasus.projects.bank.eco.feature.product.presentation.states.ProductState

@Composable
fun ProductScreenRoot(navigationManager: NavigationManager) {
    MainBaseScreen<ProductViewModel>(navigationManager, BottomNavbarScreen.Product.title) { vm ->
        val state = vm.state.collectAsStateWithLifecycle()

        ProductScreen(
            state = state.value,
            onAction = vm::handleIntent
        )
    }
}

@Composable
fun ProductScreen(
    state: ProductState,
    onAction: (ProductIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

    }
}

@Preview(
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true
)
@Composable
fun ProductScreenLightPreview() {
    EcoTheme {
        ProductScreen(
            state = ProductState(isLoading = false),
            onAction = {}
        )
    }
}

@Preview(
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun ProductScreenDarkPreview() {
    EcoTheme {
        ProductScreen(
            state = ProductState(isLoading = false),
            onAction = {}
        )
    }
}