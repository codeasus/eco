package codeasus.projects.bank.eco.feature.card.presentation

import android.content.res.Configuration
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import codeasus.projects.bank.eco.R
import codeasus.projects.bank.eco.core.navigation.NavigationManager
import codeasus.projects.bank.eco.core.ui.shared.view.MenuItem
import codeasus.projects.bank.eco.core.ui.shared.view.base.BaseScaffold
import codeasus.projects.bank.eco.core.ui.shared.view.base.BaseScreen
import codeasus.projects.bank.eco.core.ui.shared.view.card.BankCardUnknown
import codeasus.projects.bank.eco.core.ui.shared.view.models.BankAccountUi
import codeasus.projects.bank.eco.core.ui.shared.view.utils.DataSourceDefaults
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme
import codeasus.projects.bank.eco.core.ui.shared.view.states.BankAccountUiState
import codeasus.projects.bank.eco.feature.card.presentation.states.CardFlipState
import codeasus.projects.bank.eco.feature.card.presentation.states.CardIntent
import codeasus.projects.bank.eco.feature.card.presentation.states.CardState
import codeasus.projects.bank.eco.feature.card.presentation.utils.CardInstantAction
import codeasus.projects.bank.eco.feature.card.presentation.utils.CardMenuItems

@Composable
fun CardScreenRoot(navigationManager: NavigationManager, bankAccountId: Long) {
    BaseScreen<CardViewModel> { vm ->
        val state = vm.state.collectAsStateWithLifecycle()

        LaunchedEffect(bankAccountId) {
            vm.handleIntent(CardIntent.LoadCard(bankAccountId))
        }

        CardScreen(
            state = state.value,
            onAction = vm::handleIntent,
            onNavigateUp = { navigationManager.navigateUp() }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardScreen(
    state: CardState,
    onAction: (CardIntent) -> Unit,
    onNavigateUp: () -> Unit = {}
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    BaseScaffold(
        topBar = {
            CardTopNavbar(
                title = if (state.bankAccountUiState is BankAccountUiState.Success<BankAccountUi>) state.bankAccountUiState.data.name else "",
                scrollBehavior = scrollBehavior
            ) {
                onNavigateUp()
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (val bankAccount = state.bankAccountUiState) {
                is BankAccountUiState.Idle -> {}
                is BankAccountUiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        BankCardUnknown(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(240.dp),
                            state.bankAccountUiState
                        )
                    }
                }

                is BankAccountUiState.NotFound -> {}
                is BankAccountUiState.Success<BankAccountUi> -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        FlipCard(state.cardFlipState, bankAccount.data)
                    }
                }
            }

            CardDetailsBottomSheet(
                bankAccountUiState = state.bankAccountPrivateDataUiState,
                isVisible = state.showBottomSheet
            ) {
                onAction(CardIntent.HideBottomSheet)
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .clip(RoundedCornerShape(32.dp))
                        .background(color = MaterialTheme.colorScheme.tertiary)
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    text = "Current Balance",
                    style = TextStyle(color = MaterialTheme.colorScheme.onTertiary)
                )
                Spacer(modifier = Modifier.height(8.dp))
                when (val bankAccountUiState = state.bankAccountUiState) {
                    is BankAccountUiState.Idle -> {}
                    is BankAccountUiState.Loading -> {}
                    is BankAccountUiState.NotFound -> {}
                    is BankAccountUiState.Success<BankAccountUi> -> {
                        val bankAccount = bankAccountUiState.data
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                modifier = Modifier.size(48.dp),
                                painter = painterResource(id = bankAccount.currency.icon),
                                contentDescription = "Current balance"
                            )
                            Text(
                                text = bankAccount.balance.toString(),
                                style = TextStyle(
                                    fontSize = MaterialTheme.typography.displayMedium.fontSize,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CardInstantAction(
                    R.drawable.ic_reveal,
                    "Reveal",
                    enabled = state.bankAccountUiState is BankAccountUiState.Success<BankAccountUi>
                ) {
                    onAction(CardIntent.ShowBottomSheet)
                }
                Spacer(modifier = Modifier.width(24.dp))
                CardInstantAction(
                    R.drawable.ic_flip,
                    "Flip",
                    enabled = state.bankAccountUiState is BankAccountUiState.Success<BankAccountUi>
                ) {
                    onAction(CardIntent.FlipCard)
                }
                Spacer(modifier = Modifier.width(24.dp))
                CardInstantAction(
                    R.drawable.ic_freeze,
                    "Freeze",
                    enabled = state.bankAccountUiState is BankAccountUiState.Success<BankAccountUi>
                ) {
                    onAction(CardIntent.FreezeCard)
                }
            }

            Spacer(modifier = Modifier.width(24.dp))

            Text(
                text = "Card management",
                style = TextStyle(fontSize = MaterialTheme.typography.headlineSmall.fontSize)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                CardMenuItems.value.forEach { cardMenuItem ->
                    MenuItem(cardMenuItem) {
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun CardScreenLightPreview() {
    EcoTheme {
        CardScreen(
            state = CardState(
                bankAccountUiState = BankAccountUiState.Success(DataSourceDefaults.unknownUser.second[0]),
                cardFlipState = CardFlipState.FRONT,
            ),
            onAction = {}
        )
    }
}

@Preview(showSystemUi = true, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CardScreenDarkPreview() {
    EcoTheme {
        CardScreen(
            state = CardState(
                bankAccountUiState = BankAccountUiState.Success(DataSourceDefaults.unknownUser.second[1]),
                cardFlipState = CardFlipState.BACK,
            ),
            onAction = {}
        )
    }
}
