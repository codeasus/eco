package codeasus.projects.bank.eco.feature.view_transaction.view

import android.content.res.Configuration
import androidx.compose.foundation.background
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
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import codeasus.projects.bank.eco.R
import codeasus.projects.bank.eco.core.ui.shared.view.models.TransactionUi
import codeasus.projects.bank.eco.core.ui.shared.view.transaction.getTransactionStatusColorByStatus
import codeasus.projects.bank.eco.core.ui.shared.view.transaction.getTransactionStatusIconByStatus
import codeasus.projects.bank.eco.core.ui.shared.view.utils.DataSourceDefaults
import codeasus.projects.bank.eco.core.ui.shared.view.utils.ellipsisText
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme
import codeasus.projects.bank.eco.core.ui.theme.textLabel
import codeasus.projects.bank.eco.feature.utils.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionBottomSheet(
    transactionUiState: UiState<TransactionUi>,
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
            when (transactionUiState) {
                is UiState.Success -> {
                    val transactionUi = transactionUiState.data
                    val statusColor = getTransactionStatusColorByStatus(transactionUi.status)
                    val statusIcon = getTransactionStatusIconByStatus(transactionUi.status)

                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .transactionStatusBackground(statusColor),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Icon(
                                    modifier = Modifier.size(32.dp),
                                    painter = painterResource(id = statusIcon),
                                    contentDescription = "Transaction status icon",
                                    tint = statusColor
                                )
                                Text(
                                    text = transactionUi.status.label,
                                    style = TextStyle(
                                        fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                                        fontWeight = FontWeight.SemiBold,

                                    )
                                )
                            }
                        }
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 28.dp)
                                .padding(bottom = 36.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(horizontal = 28.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = transactionUi.type.label,
                                    style = TextStyle(
                                        fontSize = MaterialTheme.typography.displaySmall.fontSize,
                                        fontWeight = FontWeight.SemiBold,
                                    )
                                )
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        modifier = Modifier.size(48.dp),
                                        painter = painterResource(id = transactionUi.currency.icon),
                                        contentDescription = "Current balance"
                                    )
                                    Text(
                                        text = transactionUi.amount,
                                        style = TextStyle(
                                            fontSize = MaterialTheme.typography.displayMedium.fontSize,
                                            fontWeight = FontWeight.SemiBold,
                                        )
                                    )
                                }
                                Text(text = "Rate: ${transactionUi.rate}")
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(25.dp))
                                    .background(color = MaterialTheme.colorScheme.surface)
                                    .padding(vertical = 18.dp, horizontal = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    BankIcon()
                                    Column {
                                        Text(text = "From:", style = TextStyle(color = textLabel))
                                        Text(text = "...${transactionUi.accountNumberFrom.takeLast(4)}")
                                    }
                                }

                                Icon(
                                    modifier = Modifier.size(28.dp),
                                    painter = painterResource(id = R.drawable.ic_transfer),
                                    contentDescription = "Transfer",
                                    tint = MaterialTheme.colorScheme.secondary
                                )

                                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Column {
                                        Text(text = "To:", style = TextStyle(color = textLabel))
                                        Text(text = "...${transactionUi.accountNumberTo.takeLast(4)}")
                                    }
                                    BankIcon()
                                }
                            }

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "Created: ${transactionUi.createdAt}",
                                    style = TextStyle(color = textLabel)
                                )
                                Text(
                                    text = "ID: ${ellipsisText(transactionUi.id)}",
                                    style = TextStyle(color = textLabel)
                                )
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TransactionActionButton(R.drawable.ic_add_note, "Add note", true) { }
                                Spacer(modifier = Modifier.width(24.dp))
                                TransactionActionButton(R.drawable.ic_split, "Split", true) { }
                                Spacer(modifier = Modifier.width(24.dp))
                                TransactionActionButton(R.drawable.ic_share, "Share", true) { }
                                Spacer(modifier = Modifier.width(24.dp))
                                TransactionActionButton(R.drawable.ic_download, "Download", true) { }
                            }
                        }
                    }
                }

                is UiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(text = "Loading")
                        }
                    }
                }

                is UiState.Empty -> {}
                is UiState.Error -> {}
            }
        }
    }
}

@Preview(showSystemUi = false, showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TransactionBottomSheetPreview() {
    EcoTheme {
        TransactionBottomSheet(
            transactionUiState = UiState.Success(DataSourceDefaults.getTransactions()[0]),
//            transactionUiState = UiState.Loading,
            isVisible = true,
        ) {

        }
    }
}