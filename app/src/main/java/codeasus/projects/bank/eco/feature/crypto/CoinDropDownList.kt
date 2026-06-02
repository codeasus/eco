package codeasus.projects.bank.eco.feature.crypto

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import codeasus.projects.bank.eco.core.ui.shared.view.utils.DataSourceDefaults
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme
import codeasus.projects.bank.eco.domain.remote.model.crypto.Coin
import codeasus.projects.bank.eco.feature.utils.UiState

@Composable
fun CoinDropDownList(
    coins: UiState<List<Coin>>,
    selectedCoin: UiState<Coin>,
    onCoinSelected: (Coin) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val coinShape = RoundedCornerShape(24.dp)

    Column {
        Surface(
            modifier = Modifier
                .widthIn(min = 64.dp)
                .border(
                    width = 1.dp,
                    color = Color.White,
                    shape = coinShape
                )
                .clip(coinShape)
                .clickable { expanded = true },
            shape = coinShape,
            color = Color.Transparent
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                when(selectedCoin) {
                    is UiState.Success -> {
                        Text(text = selectedCoin.data.name, color = Color.White)
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Expand",
                            tint = Color.White
                        )
                    }

                    is UiState.Loading, is UiState.Idle -> {
                        Text(text = "Loading", color = Color.White)
                        CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 3.dp)
                    }


                    is UiState.Empty -> {
                        Text(text = "Empty")
                    }

                    else -> {}
                }
            }
        }

        if (coins is UiState.Success && coins.data.isNotEmpty()) {
            DropdownMenu(
                shape = RoundedCornerShape(18.dp),
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                coins.data.forEach { coin ->
                    DropdownMenuItem(
                        text = { Text(text = coin.name) },
                        onClick = {
                            onCoinSelected(coin)
                            expanded = false
                        })
                }
            }
        }
    }
}

@Preview(
    showSystemUi = false,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    backgroundColor = 0xFFA4B3EAFF
)
@Composable
fun CurrencyDropDownListPreview() {
    EcoTheme {
        CoinDropDownList(
            coins = UiState.Success(DataSourceDefaults.getCoins().take(0)),
            selectedCoin = UiState.Loading //UiState.Success(DataSourceDefaults.getCoins()[0])
        ) {

        }
    }
}