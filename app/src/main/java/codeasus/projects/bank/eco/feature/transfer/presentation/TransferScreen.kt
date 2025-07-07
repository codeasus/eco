package codeasus.projects.bank.eco.feature.transfer.presentation

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import codeasus.projects.bank.eco.R
import codeasus.projects.bank.eco.core.navigation.BottomNavbarScreen
import codeasus.projects.bank.eco.core.navigation.NavigationManager
import codeasus.projects.bank.eco.core.ui.shared.view.Profiles
import codeasus.projects.bank.eco.core.ui.shared.view.base.MainBaseScreen
import codeasus.projects.bank.eco.core.ui.shared.view.utils.InputValidationResult
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme
import codeasus.projects.bank.eco.domain.local.model.enums.Currency
import codeasus.projects.bank.eco.feature.transfer.states.InputField
import codeasus.projects.bank.eco.feature.transfer.states.TransferIntent
import codeasus.projects.bank.eco.feature.transfer.states.TransferState
import codeasus.projects.bank.eco.feature.transfer.utils.CardNumberVisualTransformation
import codeasus.projects.bank.eco.feature.utils.UiState

@Composable
fun TransferScreenRoot(navigationManager: NavigationManager) {
    MainBaseScreen<TransferViewModel>(navigationManager, BottomNavbarScreen.Transfer.title) { vm ->
        val state = vm.state.collectAsStateWithLifecycle()

        TransferScreen(state.value, vm::handleIntent)
    }
}

@Composable
fun TransferScreen(
    state: TransferState,
    onAction: (TransferIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Profiles(state.customers) { customer -> onAction(TransferIntent.SelectCustomer(customer)) }

        Text(
            modifier = Modifier.align(Alignment.Start),
            text = "Card Details",
            style = TextStyle(fontSize = MaterialTheme.typography.headlineMedium.fontSize)
        )

        if(state.binLookupResultState is UiState.Success) {
            BankAccountBinView(state.binLookupResultState.data)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.75F))
                .padding(vertical = 24.dp, horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.transaction.accountNumber,
                onValueChange = {
                    if (it.length > 16) return@OutlinedTextField
                    onAction(TransferIntent.SetAccountNumber(it))
                },
                placeholder = { Text("Recipient's card number") },
                shape = RoundedCornerShape(24.dp),
                singleLine = true,
                visualTransformation = CardNumberVisualTransformation(),
                supportingText = {
                    state.inputFieldValidationStates[InputField.CardNumber].let {
                        if (it is InputValidationResult.Invalid) Text(it.errorMessage)
                    }
                },
                isError = state.inputFieldValidationStates[InputField.CardNumber] is InputValidationResult.Invalid,
                leadingIcon = {
                    Icon(
                        painter = painterResource(
                            id = when(val binLookupResult = state.binLookupResultState) {
                                is UiState.Success -> {
                                    when(binLookupResult.data.scheme) {
                                        "VISA" -> R.drawable.ic_visa_outlined
                                        "MASTERCARD" -> R.drawable.ic_master_outlined
                                        "AMERICAN EXPRESS" -> R.drawable.ic_amex_outlined
                                        else -> R.drawable.ic_card
                                    }
                                }
                                is UiState.Empty, UiState.Loading, is UiState.Error -> {
                                    R.drawable.ic_card
                                }
                            }
                        ),
                        contentDescription = "Card number"
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                )
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.transaction.accountName,
                onValueChange = { onAction(TransferIntent.SetBeneficiaryName(it)) },
                placeholder = { Text("Recipient's name") },
                shape = RoundedCornerShape(24.dp),
                singleLine = true,
                supportingText = {
                    state.inputFieldValidationStates[InputField.RecipientName].let {
                        if (it is InputValidationResult.Invalid) Text(it.errorMessage)
                    }
                },
                isError = state.inputFieldValidationStates[InputField.RecipientName] is InputValidationResult.Invalid,
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_user),
                        contentDescription = "Name"
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                var transferAmountText by remember { mutableStateOf("") }

                CurrencyDropDownList(
                    Currency.entries.toTypedArray(),
                    state.transaction.currency,
                ) { currency -> onAction(TransferIntent.SelectCurrency(currency)) }

                OutlinedTextField(
                    modifier = Modifier.weight(1.0f),
                    value = transferAmountText,
                    onValueChange = {
                        transferAmountText = it
                        onAction(TransferIntent.SetTransferAmount(it))
                    },
                    placeholder = { Text("Transfer amount") },
                    shape = RoundedCornerShape(24.dp),
                    singleLine = true,
                    supportingText = {
                        state.inputFieldValidationStates[InputField.TransferAmount].let {
                            if (it is InputValidationResult.Invalid) Text(it.errorMessage)
                        }
                    },
                    isError = state.inputFieldValidationStates[InputField.TransferAmount] is InputValidationResult.Invalid,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = state.transaction.currency.icon),
                            contentDescription = "Currency"
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    )
                )
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .align(Alignment.CenterHorizontally),
                onClick = {}
            ) {
                Text(text = "Transfer")
            }
        }
    }
}

@Preview(showSystemUi = false, uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun TransferScreenPreview() {
    EcoTheme {
        TransferScreen(TransferState()) {}
    }
}