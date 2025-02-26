package codeasus.projects.bank.eco.feature.subgraphone.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import codeasus.projects.bank.eco.core.navigation.AppNavigator
import codeasus.projects.bank.eco.core.navigation.Screen
import codeasus.projects.bank.eco.core.ui.shared.view.ItemsContainer
import codeasus.projects.bank.eco.core.ui.shared.view.base.BaseScreen
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme

@Composable
fun SubgraphOneScreen(navigator: AppNavigator) {
    BaseScreen<SubgraphOneViewModel>(navigator = navigator) {
        Spacer(Modifier.height(32.dp))
        ItemsContainer {
            Button(
                modifier = Modifier.width(156.dp),
                onClick = {
                    navigator.navigateTo(Screen.SubgraphTwo)
                }
            ) {
                Text("Next")
            }
        }
    }
}

@Preview(
    showSystemUi = false,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun SubgraphOneScreenPreview() {
    EcoTheme {
        val nav = AppNavigator(rememberNavController())
        SubgraphOneScreen(nav)
    }
}