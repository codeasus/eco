package codeasus.projects.bank.eco.feature.request_money.view

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import codeasus.projects.bank.eco.R
import codeasus.projects.bank.eco.core.ui.shared.mappers.toCustomerUi
import codeasus.projects.bank.eco.core.ui.shared.view.Profiles
import codeasus.projects.bank.eco.core.ui.shared.view.models.CustomerUi
import codeasus.projects.bank.eco.core.ui.shared.view.utils.DataSourceDefaults
import codeasus.projects.bank.eco.core.ui.shared.view.utils.InputField
import codeasus.projects.bank.eco.core.ui.shared.view.utils.InputValidationResult
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme
import codeasus.projects.bank.eco.feature.request_money.state.RequestMoneyState
import codeasus.projects.bank.eco.feature.request_money.utils.bottomBorderShape
import codeasus.projects.bank.eco.feature.request_money.utils.requestMonetBottomSheetBackground
import codeasus.projects.bank.eco.feature.utils.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestMoneyBottomSheet(
    requestMoneyState: RequestMoneyState,
    isVisible: Boolean,
    onProfileSelected: (CustomerUi) -> Unit,
    onSetTransferAmount: (String) -> Unit,
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
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .requestMonetBottomSheetBackground(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Request Money",
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(horizontal = 28.dp)
                        .padding(bottom = 24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(48.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Column(
                            modifier = Modifier.border(
                                width = 2.dp,
                                color = MaterialTheme.colorScheme.primary,
                                shape = bottomBorderShape(2.dp)
                            ),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Amount", color = Color.Gray, fontSize = 18.sp)
                            BasicTextField(
                                modifier = Modifier.width(196.dp),
                                value = requestAmountText,
                                onValueChange = {
                                    requestAmountText = it
                                    onSetTransferAmount(it)
                                },
                                textStyle = TextStyle(
                                    color = Color.White,
                                    fontSize = 56.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                ),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Done
                                ),
                                singleLine = true,
                                decorationBox = { innerTextField ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        when (val bankAccount =
                                            requestMoneyState.beneficiaryBankAccount) {
                                            is UiState.Success -> {
                                                Icon(
                                                    modifier = Modifier.size(56.dp),
                                                    painter = painterResource(id = bankAccount.data.currency.icon),
                                                    tint = MaterialTheme.colorScheme.primary,
                                                    contentDescription = "Name"
                                                )
                                            }

                                            is UiState.Loading -> {
                                                CircularProgressIndicator()
                                            }

                                            else -> {}
                                        }
                                        innerTextField()
                                    }
                                }
                            )
                        }
                        Text(
                            text = requestMoneyState.inputFieldValidationStates[InputField.TransferAmount].let {
                                when(it) {
                                    is InputValidationResult.Invalid -> it.errorMessage
                                    else -> ""
                                }
                            },
                            color = Color.Red
                        )
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
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

                    PaymentDropdownMenu()

                    Button(modifier = Modifier.fillMaxWidth(), onClick = {}) {
                        Text("Request")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentDropdownMenu() {
    var expanded by remember { mutableStateOf(false) }
    var selectedPayment by remember { mutableStateOf("Select Request Method") }

    val paymentOptions = listOf(
        Pair(R.drawable.ic_google_pay, "Google Pay"),
        Pair(R.drawable.ic_paypal, "PayPal"),
        Pair(R.drawable.ic_card, "Direct Bank Account"),
    )

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            OutlinedTextField(
                value = selectedPayment,
                shape = RoundedCornerShape(32.dp),
                onValueChange = {},
                readOnly = true,
                label = { Text("Another Top-Up Method") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown arrow"
                    )
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                shape = RoundedCornerShape(32.dp),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                paymentOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option.second) },
                        leadingIcon = {
                            if(option.first == R.drawable.ic_card) {
                                Icon(
                                    painter = painterResource(id = option.first),
                                    contentDescription = "Card",
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                                return@DropdownMenuItem
                            }
                            Image(
                                painter = painterResource(id = option.first),
                                contentDescription = option.second
                            )
                        },
                        onClick = {
                            selectedPayment = option.second
                            expanded = false
                        }
                    )
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
                friends = UiState.Success(
                    DataSourceDefaults.getCustomers().filter { it.isFriend }
                        .map { it.toCustomerUi() })
            ),
            isVisible = true,
            onProfileSelected = {},
            onSetTransferAmount = {}
        ) {

        }
    }
}