package codeasus.projects.bank.eco.feature.request_money.view

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import codeasus.projects.bank.eco.R
import codeasus.projects.bank.eco.core.ui.shared.mappers.toCustomerUi
import codeasus.projects.bank.eco.core.ui.shared.view.Profiles
import codeasus.projects.bank.eco.core.ui.shared.view.card.CircularShufflingCards
import codeasus.projects.bank.eco.core.ui.shared.view.models.BankAccountUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.CustomerUi
import codeasus.projects.bank.eco.core.ui.shared.view.utils.DataSourceDefaults
import codeasus.projects.bank.eco.core.ui.shared.view.utils.InputField
import codeasus.projects.bank.eco.core.ui.shared.view.utils.InputValidationResult
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme
import codeasus.projects.bank.eco.feature.request_money.state.RequestMoneyState
import codeasus.projects.bank.eco.feature.request_money.utils.requestMonetBottomSheetBackground
import codeasus.projects.bank.eco.feature.utils.UiState
import codeasus.projects.bank.eco.feature.view_transaction.view.TransactionActionButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestMoneyBottomSheet(
    requestMoneyState: RequestMoneyState,
    isVisible: Boolean,
    onProfileSelected: (CustomerUi) -> Unit,
    onSetTransferAmount: (String) -> Unit,
    setBeneficiaryBankAccount: (BankAccountUi) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    var requestAmountText by remember { mutableStateOf("0.0") }

    if (isVisible) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
            containerColor = MaterialTheme.colorScheme.surface,
            tonalElevation = 4.dp,
            dragHandle = { BottomSheetDefaults.DragHandle() }
        ) {
            Column(
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .animateContentSize(),
                verticalArrangement = Arrangement.spacedBy(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .requestMonetBottomSheetBackground(MaterialTheme.colorScheme.primary),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Request Money",
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                            fontWeight = FontWeight.SemiBold
                        )
                    )

                    when (val accounts = requestMoneyState.accounts) {
                        is UiState.Success -> {
                            if (accounts.data.isNotEmpty()) {
                                CircularShufflingCards(accounts.data) {
                                    setBeneficiaryBankAccount(accounts.data[it])
                                }
                            }
                        }

                        else -> {}
                    }

                    Text(text = "Amount", fontSize = 18.sp)
                    TextField(
                        modifier = Modifier.width(172.dp),
                        value = requestAmountText,
                        onValueChange = {
                            requestAmountText = it
                            onSetTransferAmount(it)
                        },
                        singleLine = true,
                        textStyle = TextStyle(
                            color = Color.White,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            errorContainerColor = Color.Transparent,
                        ),
                        leadingIcon = {
                            when (val bankAccount = requestMoneyState.beneficiaryBankAccount) {
                                is UiState.Success -> {
                                    Icon(
                                        modifier = Modifier.size(32.dp),
                                        painter = painterResource(id = bankAccount.data.currency.icon),
                                        tint = MaterialTheme.colorScheme.primary,
                                        contentDescription = bankAccount.data.currency.name
                                    )
                                }

                                is UiState.Loading -> {
                                    CircularProgressIndicator()
                                }

                                else -> {}
                            }
                        },
                        isError = requestMoneyState.inputFieldValidationStates[InputField.TransferAmount] is InputValidationResult.Invalid,
                        supportingText = {
                            requestMoneyState.inputFieldValidationStates[InputField.TransferAmount].let {
                                if (it is InputValidationResult.Invalid) Text(it.errorMessage)
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        )
                    )
                }

                var friendSectionVisibility by remember { mutableStateOf(false) }
                var cardSectionVisibility by remember { mutableStateOf(false) }

                Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                    TransactionActionButton(R.drawable.ic_user, "Via Friend", true) {
                        cardSectionVisibility = false
                        friendSectionVisibility = !friendSectionVisibility
                    }
                    TransactionActionButton(R.drawable.ic_google_pay_simple, "Google Pay", true) { }
                    TransactionActionButton(R.drawable.ic_paypal_simple, "PayPal", true) { }
                    TransactionActionButton(R.drawable.ic_card, "Via Card", true) {
                        friendSectionVisibility = false
                        cardSectionVisibility = !cardSectionVisibility
                    }
                }

                AnimatedVisibility(
                    visible = friendSectionVisibility,
                    enter = slideInHorizontally(initialOffsetX = { -it }) +
                            expandVertically(expandFrom = Alignment.Top) +
                            fadeIn(),
                    exit = slideOutHorizontally(targetOffsetX = { -it }) +
                            shrinkVertically(shrinkTowards = Alignment.Top) +
                            fadeOut()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 28.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        when (requestMoneyState.friends) {
                            is UiState.Success -> {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = "From friends:", fontSize = 18.sp)
                                    Text(
                                        text = requestMoneyState.friends.data.size.toString(),
                                        fontSize = 18.sp
                                    )
                                }
                                Profiles(requestMoneyState.friends.data) {
                                    onProfileSelected(it)
                                }
                            }

                            is UiState.Loading -> {
                                Box(modifier = Modifier.height(128.dp)) {
                                    CircularProgressIndicator()
                                }
                            }

                            else -> {}
                        }
                    }
                }

                AnimatedVisibility(friendSectionVisibility || cardSectionVisibility) {
                    when (val beneficiaryBankAccount = requestMoneyState.beneficiaryBankAccount) {
                        is UiState.Success -> {
                            Button(onClick = {}) {
                                Text(text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(baselineShift = BaselineShift(0.15f))) { append("Request ") }
                                    withStyle(style = SpanStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)) { append("${requestMoneyState.amount}${beneficiaryBankAccount.data.currency.symbol}") }
                                })
                            }
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = false, showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun RequestMoneyBottomSheetPreview() {
    EcoTheme {
        RequestMoneyBottomSheet(
            requestMoneyState = RequestMoneyState(
                beneficiaryBankAccount = UiState.Success(DataSourceDefaults.unknownUser.second[0]),
                friends = UiState.Success(DataSourceDefaults.getCustomers().filter { it.isFriend }
                    .map { it.toCustomerUi() }),
                accounts = UiState.Success(
                    listOf(
                        DataSourceDefaults.unknownUser.second[1],
                        DataSourceDefaults.unknownUser.second[0]
                    )
                )
            ),
            isVisible = true,
            onProfileSelected = {},
            setBeneficiaryBankAccount = {},
            onSetTransferAmount = {}
        ) {

        }
    }
}