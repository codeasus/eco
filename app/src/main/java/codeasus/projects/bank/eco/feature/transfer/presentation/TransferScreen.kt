package codeasus.projects.bank.eco.feature.transfer.presentation

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import codeasus.projects.bank.eco.core.navigation.AppNavigator
import codeasus.projects.bank.eco.core.ui.shared.view.base.BaseScreen
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme

@Composable
fun TransferScreen(navigator: AppNavigator) {
    BaseScreen<TransferViewModel>(navigator = navigator) { vm ->

    }
}

@Preview(
    showSystemUi = false,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun TransferScreenPreview() {
    EcoTheme {
        val nav = AppNavigator(rememberNavController())
        TransferScreen(nav)
    }
}