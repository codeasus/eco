package codeasus.projects.bank.eco.feature.transfer.presentation

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogWindowProvider
import androidx.navigation.compose.rememberNavController
import codeasus.projects.bank.eco.R
import codeasus.projects.bank.eco.core.navigation.AppNavigator
import codeasus.projects.bank.eco.core.ui.shared.view.ItemsContainer
import codeasus.projects.bank.eco.core.ui.shared.view.Profiles
import codeasus.projects.bank.eco.core.ui.shared.view.base.BaseScreen
import codeasus.projects.bank.eco.core.ui.shared.view.card.Cards
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme

@Composable
fun TransferScreen(navigator: AppNavigator) {
    var showDialog by rememberSaveable { mutableStateOf(false) }

    BaseScreen<TransferViewModel>(navigator = navigator) { vm ->

        val customers = vm.customerStates.collectAsState().value

        Spacer(Modifier.height(16.dp))
        Profiles(customers) {
            vm.selectCustomer(it)
        }
        Spacer(Modifier.height(18.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Text(modifier = Modifier.align(Alignment.Start), text = "Transfer From")
        }
        Cards(vm.userState.value?.bankAccounts ?: emptyList())
        Spacer(Modifier.height(18.dp))
        ItemsContainer {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Instant (EFTN)")
                Spacer(Modifier.width(8.dp))
                Switch(
                    checked = vm.isStandardTransferEnabled,
                    colors = SwitchDefaults.colors(checkedIconColor = MaterialTheme.colorScheme.primary),
                    onCheckedChange = { vm.enableStandardTransfer() }
                )
                Spacer(Modifier.width(8.dp))
                Text(text = "Standard (BEFTN)")
            }
            var amount by remember { mutableStateOf("") }
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                placeholder = { Text("Transfer Amount") },
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                )
            )
            Spacer(Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = vm.isTermsAndConditionsAccepted,
                    colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary),
                    onCheckedChange = { vm.acceptPrivacyPolicy() }
                )
                TermsAndConditionsText("https://www.example.com/terms")
            }
            Spacer(Modifier.height(8.dp))
            Button(
                modifier = Modifier.width(156.dp),
                enabled = vm.isTermsAndConditionsAccepted,
                onClick = {
                    showDialog = !showDialog
                }
            ) {
                Text("Transfer")
            }
            if (showDialog) {
                Dialog(onDismissRequest = { showDialog = false }) {
                    (LocalView.current.parent as DialogWindowProvider).window.setDimAmount(
                        0.8f
                    )
                    Box(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                shape = RoundedCornerShape(32.dp)
                            )
                            .padding(32.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                modifier = Modifier.size(96.dp),
                                painter = painterResource(R.drawable.ic_success),
                                contentDescription = "Success sign",
                                colorFilter = ColorFilter.tint(Color(98, 192, 98, 255))
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Money is on the way",
                                style = MaterialTheme.typography.headlineSmall
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Estimated arrival is 5 minutes")
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = { showDialog = false }) {
                                Text("Close")
                            }
                        }
                    }
                }
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
fun TransferScreenPreview() {
    EcoTheme {
        val nav = AppNavigator(rememberNavController())
        TransferScreen(nav)
    }
}