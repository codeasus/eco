package codeasus.projects.bank.eco.feature.reward.presentation

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import codeasus.projects.bank.eco.core.navigation.AppNavigator
import codeasus.projects.bank.eco.core.ui.shared.view.base.BaseScreen
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme

@Composable
fun RewardScreen(navigator: AppNavigator) {
    BaseScreen<RewardViewModel>(navigator = navigator) {

    }
}

@Preview(
    showSystemUi = false,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun RewardScreenPreview() {
    EcoTheme {
        val nav = AppNavigator(rememberNavController())
        RewardScreen(nav)
    }
}