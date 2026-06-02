package codeasus.projects.bank.eco.feature.crypto.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import codeasus.projects.bank.eco.core.ui.shared.view.utils.DataSourceDefaults
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme
import codeasus.projects.bank.eco.domain.remote.model.crypto.Coin

@Composable
fun Coins(
    coins: List<Coin>,
    priceChangeInPercentByInterval: PriceChangeInPercentByInterval,
    onCoinSelected: (String) -> Unit
) {
    Column {
        for (coin in coins.take(10)) {
            CoinListItem(coin, priceChangeInPercentByInterval, onCoinSelected)
        }
    }
}

@Preview(
    showSystemUi = false,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun CoinsPreview() {
    EcoTheme {
        Coins(DataSourceDefaults.getCoins(), PriceChangeInPercentByInterval.DAY) {}
    }
}