package codeasus.projects.bank.eco.feature.home.presentation

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import codeasus.projects.bank.eco.core.navigation.AppNavigator
import codeasus.projects.bank.eco.core.navigation.Screen
import codeasus.projects.bank.eco.core.ui.shared.view.base.BaseScreen
import codeasus.projects.bank.eco.core.ui.shared.view.transaction.Transactions
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme

@Composable
fun HomeScreen(navigator: AppNavigator) {
    BaseScreen<HomeViewModel>(navigator = navigator) { vm ->

        val transactions = vm.customerTransactionPairsState.collectAsState().value

        Spacer(Modifier.height(32.dp))
        Box(modifier = Modifier.padding(18.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(360.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25F))
                    .padding(12.dp)
            ) {
                Transactions(transactions)
            }
        }
        Spacer(Modifier.height(32.dp))
        Button(
            modifier = Modifier.width(156.dp),
            onClick = {
                navigator.navigateTo(Screen.Transfer)
            }
        ) {
            Text("Transfer")
        }
        Button(
            modifier = Modifier.width(156.dp),
            onClick = {  }
        ) {
            Text("Empty")
        }
    }
}

@Preview(
    showSystemUi = false,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun HomeScreenPreview() {
    EcoTheme {
        val nav = AppNavigator(rememberNavController())
        HomeScreen(nav)
    }
}