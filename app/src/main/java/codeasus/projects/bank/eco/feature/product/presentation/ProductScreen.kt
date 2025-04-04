package codeasus.projects.bank.eco.feature.product.presentation

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import codeasus.projects.bank.eco.core.navigation.BottomNavbarScreen
import codeasus.projects.bank.eco.core.navigation.NavigationManager
import codeasus.projects.bank.eco.core.ui.shared.view.base.MainBaseScreen
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme

@Composable
fun ProductScreen(navigationManager: NavigationManager) {
    MainBaseScreen<ProductViewModel>(navigationManager, BottomNavbarScreen.Product.title) {

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