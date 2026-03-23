package codeasus.projects.bank.eco.feature.request_money.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentDropdownMenu(paymentOptions: List<Pair<Int, String>>) {
    var expanded by remember { mutableStateOf(false) }
    var selectedPayment by remember { mutableStateOf(paymentOptions[0]) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .menuAnchor(type = MenuAnchorType.PrimaryNotEditable)
                    .fillMaxWidth(),
                value = selectedPayment.second,
                shape = RoundedCornerShape(32.dp),
                onValueChange = {},
                readOnly = true,
                label = { Text("Another Top-Up Method") },
                leadingIcon = {
                    Image(
                        painter = painterResource(id = selectedPayment.first),
                        contentDescription = selectedPayment.second
                    )
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                }
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
                            Image(
                                painter = painterResource(id = option.first),
                                contentDescription = selectedPayment.second
                            )
                        },
                        onClick = {
                            selectedPayment = option
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}