package codeasus.projects.bank.eco.feature.info.presentation

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import codeasus.projects.bank.eco.core.navigation.AppNavigator
import codeasus.projects.bank.eco.core.ui.shared.view.base.BaseScreen
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme

@Composable
fun InfoScreen(navigator: AppNavigator) {
    BaseScreen<InfoViewModel>(navigator = navigator) {

    }
}

@Preview(
    showSystemUi = false,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun InfoScreenPreview() {
    EcoTheme {
        val nav = AppNavigator(rememberNavController())
        InfoScreen(nav)
    }
}