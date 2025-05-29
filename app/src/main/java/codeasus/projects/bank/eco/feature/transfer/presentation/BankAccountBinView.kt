package codeasus.projects.bank.eco.feature.transfer.presentation

import android.content.res.Configuration
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import codeasus.projects.bank.eco.R
import codeasus.projects.bank.eco.core.ui.shared.view.utils.CountryFlags
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme
import codeasus.projects.bank.eco.domain.remote.model.banking.BinLookupModel
import codeasus.projects.bank.eco.domain.remote.model.banking.Country
import codeasus.projects.bank.eco.feature.utils.UiState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BankAccountBinView(binUiState: UiState<BinLookupModel>) {
    @Composable
    fun View(bin: BinLookupModel) {

        val shakeOffset = remember { Animatable(0f) }

        LaunchedEffect(bin) {
            shakeOffset.animateTo(
                targetValue = 0f,
                animationSpec = keyframes {
                    durationMillis = 300
                    0f at 0
                    -8f at 50
                    8f at 100
                    -6f at 150
                    6f at 200
                    -4f at 250
                    0f at 300
                }
            )
        }

        FlowRow (
            modifier = Modifier
                .fillMaxWidth()
                .offset { IntOffset(shakeOffset.value.dp.roundToPx(), 0) },
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            SuggestionChip(
                onClick = {},
                label = { bin.issuer?.let { Text(text = it) } },
                icon = {
                    Icon(
                        modifier = Modifier.size(18.dp),
                        painter = painterResource(id = R.drawable.ic_bank),
                        contentDescription = "Bank"
                    )
                },
                shape = RoundedCornerShape(18.dp)
            )
            SuggestionChip(
                onClick = {},
                label = {
                    bin.country?.a2?.let {
                        Text(text = "${CountryFlags.getFlagEmoji(it)} $it")
                    }
                },
                shape = RoundedCornerShape(18.dp)
            )
        }
    }

    when (binUiState) {
        is UiState.Success -> {
            View(binUiState.data)
        }

        is UiState.Loading, is UiState.Error -> {}
    }
}

@Preview(showSystemUi = false, uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun BankAccountBinViewPreview() {
    EcoTheme {
        val bin = BinLookupModel(
            status = "SUCCESS",
            scheme = "VISA",
            type = "DEBIT",
            issuer = "REVOLUT BANK UAB",
            cardTier = "INFINITE",
            country = Country(
                a2 = "CH",
                a3 = "CHE",
                n3 = "756",
                isd = "41",
                name = "Switzerland",
                cont = "Europe"
            ),
            luhn = true
        )
        BankAccountBinView(UiState.Success(bin))
    }
}