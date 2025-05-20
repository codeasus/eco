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
import codeasus.projects.bank.eco.feature.transfer.utils.CardNumberVisualTransformation
import codeasus.projects.bank.eco.feature.transfer.utils.Currencies

@Composable
fun TransferScreen(navigationManager: NavigationManager) {
    MainBaseScreen<TransferViewModel>(navigationManager, BottomNavbarScreen.Transfer.title) { vm ->

        val customers = vm.customers.collectAsStateWithLifecycle()
        val transactionState = vm.transactionState.collectAsStateWithLifecycle()
        val inputFieldValidationStates = vm.inputFieldValidationStates

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Profiles(customers.value) { customer -> vm.selectCustomer(customer) }
            Text(
                modifier = Modifier.align(Alignment.Start),
                text = "Card Details",
                style = TextStyle(fontSize = MaterialTheme.typography.headlineMedium.fontSize)
            )

//            Row(modifier = Modifier.fillMaxWidth().background(Color.Red)){
//                SuggestionChip(
//                    onClick = {},
//                    label = { Text(text = "Capital Bank") },
//                    shape = RoundedCornerShape(18.dp)
//                )
//            }

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
                    value = transactionState.value.accountNumber,
                    onValueChange = {
                        if (it.length > 16) return@OutlinedTextField
                        vm.setAccountNumber(it)
                    },
                    placeholder = { Text("Recipient's card number") },
                    shape = RoundedCornerShape(24.dp),
                    singleLine = true,
                    visualTransformation = CardNumberVisualTransformation(),
                    supportingText = {
                        inputFieldValidationStates["cardNumber"].let {
                            if (it is InputValidationResult.Invalid) Text(it.errorMessage)
                        }
                    },
                    isError = inputFieldValidationStates["cardNumber"] is InputValidationResult.Invalid,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_card),
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
                    value = transactionState.value.accountName,
                    onValueChange = { vm.setAccountName(it) },
                    placeholder = { Text("Recipient's name") },
                    shape = RoundedCornerShape(24.dp),
                    singleLine = true,
                    supportingText = {
                        inputFieldValidationStates["recipientName"].let {
                            if (it is InputValidationResult.Invalid) Text(it.errorMessage)
                        }
                    },
                    isError = inputFieldValidationStates["recipientName"] is InputValidationResult.Invalid,
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
                    val selectedCurrency = transactionState.value.currency
                    val currencyIcon = Currencies.getIcon(selectedCurrency)
                    var transferAmountText by remember { mutableStateOf("") }

                    CurrencyDropDownList(
                        Currencies.items,
                        selectedCurrency,
                        currencyIcon
                    ) { currency -> vm.selectCurrency(currency) }

                    OutlinedTextField(
                        modifier = Modifier.weight(1.0f),
                        value = transferAmountText,
                        onValueChange = {
                            transferAmountText = it
                            vm.setAmount(it)
                        },
                        placeholder = { Text("Transfer amount") },
                        shape = RoundedCornerShape(24.dp),
                        singleLine = true,
                        supportingText = {
                            inputFieldValidationStates["transferAmount"].let {
                                if (it is InputValidationResult.Invalid) Text(it.errorMessage)
                            }
                        },
                        isError = inputFieldValidationStates["transferAmount"] is InputValidationResult.Invalid,
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = currencyIcon),
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
}

@Preview(showSystemUi = false, uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun TransferScreenPreview() {
    EcoTheme {

    }
}