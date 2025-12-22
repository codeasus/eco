package codeasus.projects.bank.eco.feature.card.presentation

import android.content.res.Configuration
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import codeasus.projects.bank.eco.R
import codeasus.projects.bank.eco.core.navigation.NavigationManager
import codeasus.projects.bank.eco.core.ui.shared.view.base.BaseScaffold
import codeasus.projects.bank.eco.core.ui.shared.view.base.BaseScreen
import codeasus.projects.bank.eco.core.ui.shared.view.card.BankCardUnknown
import codeasus.projects.bank.eco.core.ui.shared.view.models.BankAccountUi
import codeasus.projects.bank.eco.core.ui.shared.view.states.BankAccountUiState
import codeasus.projects.bank.eco.core.ui.shared.view.transaction.Transactions
import codeasus.projects.bank.eco.core.ui.shared.view.utils.DataSourceDefaults
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme
import codeasus.projects.bank.eco.feature.card.presentation.states.CardFlipState
import codeasus.projects.bank.eco.feature.card.presentation.states.CardIntent
import codeasus.projects.bank.eco.feature.card.presentation.states.CardState
import codeasus.projects.bank.eco.feature.card.presentation.utils.CardInstantAction
import codeasus.projects.bank.eco.feature.card.presentation.utils.CardMenuItem
import codeasus.projects.bank.eco.feature.card.presentation.utils.CardMenuItems

@Composable
fun CardScreenRoot(navigationManager: NavigationManager, accountId: String) {
    BaseScreen<CardViewModel> { vm ->
        val state = vm.state.collectAsStateWithLifecycle()

        LaunchedEffect(accountId) {
            vm.handleIntent(CardIntent.LoadCard(accountId))
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
fun CardScreen(state: CardState, onAction: (CardIntent) -> Unit, onNavigateUp: () -> Unit = {}) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val lightBankCardContainerColor = Color(184, 193, 239, 255)
    val darkerBankAccountContainerColor = Color(93, 95, 138, 255)
    val frontWaveColor = Color(113, 119, 164, 255)
    val middleWaveColor = Color(80, 82, 129, 255)
    val backWaveColor = Color(54, 63, 110, 255)
    BaseScaffold(
        topBar = {
            CardTopNavbar(
                title = if (state.bankAccountUiState is BankAccountUiState.Success<BankAccountUi>) state.bankAccountUiState.data.name else "",
                color = darkerBankAccountContainerColor,
                scrollBehavior = scrollBehavior
            ) {
                onNavigateUp()
            }
        },
        floatingActionButton = {
            CardOptionsFab(
                items = CardMenuItems.value,
                onItemClick = { menuItem ->
                    when (menuItem) {
                        is CardMenuItem.Freeze -> { /* onAction(CardIntent.FreezeCard) */
                        }

                        is CardMenuItem.Limits -> { /* onAction(CardIntent.OpenLimits) */
                        }

                        is CardMenuItem.Edit -> { /* onAction(CardIntent.EditCard) */
                        }

                        is CardMenuItem.Delete -> { /* onAction(CardIntent.DeleteCard) */
                        }
                    }
                }
            )
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .drawBehind {
                        // --- SHARED BOTTOM GEOMETRY ---
                        fun Path.applyBottomCurve(width: Float, height: Float) {
                            cubicTo(
                                x1 = width * 0.75F,
                                y1 = height - 150F,
                                x2 = width * 0.4F,
                                y2 = height + 200F,
                                x3 = 0F,
                                y3 = height
                            )
                        }

                        // --- LAYER 0: (Background) ---
                        val mainBackground = Path().apply {
                            moveTo(0F, 0F)
                            lineTo(size.width, 0F)
                            lineTo(size.width, size.height)
                            applyBottomCurve(size.width, size.height)
                            close()
                        }

                        // --- WAVE 1: (Back) ---
                        val bgWaveFirst = Path().apply {
                            moveTo(0F, size.height - 50F)
                            cubicTo(
                                x1 = size.width * 0.4f, y1 = size.height,
                                x2 = size.width * 0.7f, y2 = size.height - 650F,
                                x3 = size.width, y3 = size.height - 550F
                            )
                            lineTo(size.width, size.height)
                            applyBottomCurve(size.width, size.height)
                            close()
                        }

                        // --- WAVE 2: (Middle) ---
                        val bgWaveSecond = Path().apply {
                            moveTo(0F, size.height - 30F)
                            cubicTo(
                                x1 = size.width * 0.3f, y1 = size.height,
                                x2 = size.width * 0.8f, y2 = size.height - 450F,
                                x3 = size.width, y3 = size.height - 400F
                            )
                            lineTo(size.width, size.height)
                            applyBottomCurve(size.width, size.height)
                            close()
                        }

                        // --- WAVE 3: (Front) ---
                        val bgWaveThird = Path().apply {
                            moveTo(0F, size.height - 10F)
                            cubicTo(
                                x1 = size.width * 0.3f, y1 = size.height + 50F,
                                x2 = size.width * 0.9f, y2 = size.height - 250F,
                                x3 = size.width, y3 = size.height - 250F
                            )
                            lineTo(size.width, size.height)
                            applyBottomCurve(size.width, size.height)
                            close()
                        }
                        drawPath(
                            path = mainBackground,
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    darkerBankAccountContainerColor,
                                    lightBankCardContainerColor
                                )
                            )
                        )
                        drawPath(
                            path = bgWaveFirst,
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    lightBankCardContainerColor,
                                    frontWaveColor
                                )
                            )
                        )
                        drawPath(
                            path = bgWaveSecond,
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    lightBankCardContainerColor,
                                    middleWaveColor
                                )
                            )
                        )
                        drawPath(
                            path = bgWaveThird,
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    lightBankCardContainerColor,
                                    backWaveColor
                                )
                            )
                        )
                    }
                    .padding(top = 16.dp, bottom = 18.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
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

                when (val bankAccountUiState = state.bankAccountUiState) {
                    is BankAccountUiState.Idle -> {}
                    is BankAccountUiState.Loading -> {}
                    is BankAccountUiState.NotFound -> {}
                    is BankAccountUiState.Success<BankAccountUi> -> {
                        val bankAccount = bankAccountUiState.data
                        Text(
                            text = "${bankAccount.type} \u2219 ${bankAccount.currency.code}",
                            color = Color.White
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                modifier = Modifier.size(48.dp),
                                painter = painterResource(id = bankAccount.currency.icon),
                                tint = Color.White,
                                contentDescription = "Current balance"
                            )
                            Text(
                                text = bankAccount.balance,
                                style = TextStyle(
                                    fontSize = MaterialTheme.typography.displayMedium.fontSize,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White
                                )
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
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
                        R.drawable.ic_topup,
                        "Top-up",
                        enabled = state.bankAccountUiState is BankAccountUiState.Success<BankAccountUi>
                    ) {
                        onAction(CardIntent.TopUp)
                    }
                    Spacer(modifier = Modifier.width(24.dp))
                    CardInstantAction(
                        R.drawable.ic_flip,
                        "Flip",
                        enabled = state.bankAccountUiState is BankAccountUiState.Success<BankAccountUi>
                    ) {
                        onAction(CardIntent.FlipCard)
                    }
                }
            }
            Column(
                modifier = Modifier.padding(top = 56.dp, start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Transactions",
                        style = TextStyle(fontSize = MaterialTheme.typography.headlineLarge.fontSize)
                    )
                    Text(
                        modifier = Modifier.clickable() { },
                        text = "View All",
                        style = TextStyle(color = MaterialTheme.colorScheme.secondary),
                        textDecoration = TextDecoration.Underline
                    )
                }
                Transactions(state.transactions)
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
                transactions = DataSourceDefaults.getCustomerTransactions(),
                cardFlipState = CardFlipState.FRONT,
            ),
            onAction = {

            }
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
                transactions = DataSourceDefaults.getCustomerTransactions(),
                cardFlipState = CardFlipState.FRONT,
            ),
            onAction = {

            }
        )
    }
}