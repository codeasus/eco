package codeasus.projects.bank.eco.feature.card.presentation

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import codeasus.projects.bank.eco.R
import codeasus.projects.bank.eco.core.navigation.NavigationManager
import codeasus.projects.bank.eco.core.ui.shared.view.base.BaseScaffold
import codeasus.projects.bank.eco.core.ui.shared.view.base.BaseScreen
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme

class CardManagementActionData(
    val actionId: Int,
    val actionName: String,
    @DrawableRes val actionIcon: Int
)

object CardManagementActions {
    val cardActions = listOf(
        CardManagementActionData(1, "Card Controls", R.drawable.ic_controls),
        CardManagementActionData(2, "Edit Card", R.drawable.ic_edit_card),
        CardManagementActionData(3, "Delete Card", R.drawable.ic_delete_card),
        CardManagementActionData(3, "Transactions", R.drawable.ic_transaction),
        CardManagementActionData(5, "Limits", R.drawable.ic_limit),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardScreen(navigationManager: NavigationManager, bankAccountId: String) {
    BaseScreen<CardViewModel> { vm ->

        val bankAccount = vm.bankAccount.collectAsStateWithLifecycle()
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
        var showBottomSheet by remember { mutableStateOf(false) }

        vm.loadCard(bankAccountId)

        BaseScaffold(
            topBar = {
                CardTopNavbar("Card ${bankAccount.value?.type.toString()}", scrollBehavior) {
                    navigationManager.navigateUp()
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                bankAccount.value?.let { bankAccount ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        FlipCard(vm.cardFlipState, bankAccount)
                        CardDetailsBottomSheet(
                            userBankAccountModel = bankAccount,
                            isVisible = showBottomSheet,
                            onDismiss = { showBottomSheet = false }
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CardInstantAction(R.drawable.ic_reveal, "Reveal") { showBottomSheet = true }
                    Spacer(modifier = Modifier.width(24.dp))
                    CardInstantAction(R.drawable.ic_flip, "Flip") { vm.flip() }
                    Spacer(modifier = Modifier.width(24.dp))
                    CardInstantAction(R.drawable.ic_freeze, "Freeze") { }
                }
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    text = "Card Management",
                    style = TextStyle(fontSize = MaterialTheme.typography.headlineSmall.fontSize)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    for (i in CardManagementActions.cardActions) {
                        CardManagementAction(i) {
                            when (i.actionId) {
                                0 -> {}
                                1 -> {}
                                2 -> {}
                                3 -> {}
                                4 -> {}
                                5 -> {}
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CardInstantAction(@DrawableRes iconResourceId: Int, actionName: String, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        FilledIconButton(
            modifier = Modifier.size(64.dp),
            shape = CircleShape,
            onClick = onClick
        ) {
            Icon(
                modifier = Modifier.size(28.dp),
                painter = painterResource(id = iconResourceId),
                contentDescription = actionName
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = actionName, style = TextStyle(fontSize = 12.sp))
    }
}

@Composable
fun CardManagementAction(
    cardManagementAction: CardManagementActionData,
    onClick: (actionId: Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            modifier = Modifier
                .border(1.dp, MaterialTheme.colorScheme.outline, CircleShape)
                .padding(18.dp),
            painter = painterResource(id = cardManagementAction.actionIcon),
            contentDescription = cardManagementAction.actionName
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = cardManagementAction.actionName)
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = { onClick(cardManagementAction.actionId) }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_nav_forward),
                contentDescription = "Next"
            )
        }
    }
}

@Preview(showSystemUi = false, showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun BankCardBackLightPreview() {
    EcoTheme {
        CardManagementAction(CardManagementActions.cardActions[0]) { }
    }
}