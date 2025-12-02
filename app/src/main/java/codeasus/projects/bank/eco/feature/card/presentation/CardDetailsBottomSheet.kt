package codeasus.projects.bank.eco.feature.card.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import codeasus.projects.bank.eco.R
import codeasus.projects.bank.eco.core.ui.shared.view.models.BankAccountUi
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme
import codeasus.projects.bank.eco.core.ui.shared.view.states.BankAccountUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDetailsBottomSheet(
    bankAccountUiState: BankAccountUiState<BankAccountUi>,
    isVisible: Boolean,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    if (isVisible) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
            containerColor = MaterialTheme.colorScheme.surface,
            tonalElevation = 4.dp,
            dragHandle = { BottomSheetDefaults.DragHandle() }
        ) {
            when (bankAccountUiState) {
                is BankAccountUiState.Success -> {
                    val bankAccountUi = bankAccountUiState.data
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 28.dp)
                            .padding(bottom = 36.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Card Details",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = MaterialTheme.typography.headlineSmall.fontSize
                            )
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(text = "Full Name")
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = bankAccountUi.name,
                                    style = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )
                                )
                                ButtonCopy { }
                            }
                        }

                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(text = "Card Number")
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = bankAccountUi.number,
                                    style = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )
                                )
                                ButtonCopy { }
                            }
                        }
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(text = "CVV")
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = bankAccountUi.cvv,
                                    style = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )
                                )
                                ButtonCopy { }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            modifier = Modifier.fillMaxWidth(0.5f),
                            onClick = onDismiss
                        ) {
                            Text("Done")
                        }
                    }
                }
                is BankAccountUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                        Column {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(text = "Loading")
                        }
                    }
                }
                is BankAccountUiState.NotFound -> {}
                is BankAccountUiState.Idle -> {}
            }
        }
    }
}

@Composable
fun ButtonCopy(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            modifier = Modifier.size(18.dp),
            painter = painterResource(R.drawable.ic_copy),
            contentDescription = "Copy"
        )
    }
}

@Preview(showSystemUi = false, showBackground = false)
@Composable
fun CardDetailsBottomSheetPreview() {
    EcoTheme {
        CardDetailsBottomSheet(
//            bankAccountUiState = BankAccountUiState.Success(DataSourceDefaults.unknownUser.second[0].toBankAccountUi()),
            bankAccountUiState = BankAccountUiState.Loading,
            isVisible = true,
        ) {

        }
    }
}