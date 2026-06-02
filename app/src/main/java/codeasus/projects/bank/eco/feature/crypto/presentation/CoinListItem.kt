package codeasus.projects.bank.eco.feature.crypto.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import codeasus.projects.bank.eco.core.ui.shared.view.utils.ellipsisText
import codeasus.projects.bank.eco.core.ui.shared.view.utils.formatCoinPrice
import codeasus.projects.bank.eco.core.ui.shared.view.utils.formatCoinPriceChange
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme
import codeasus.projects.bank.eco.domain.remote.model.crypto.Coin
import coil.compose.AsyncImage

@Composable
fun CoinListItem(
    coin: Coin,
    priceChangeInPercentByInterval: PriceChangeInPercentByInterval,
    onCoinSelected: (String) -> Unit
) {
    val currentChange = when(priceChangeInPercentByInterval) {
        PriceChangeInPercentByInterval.HOUR -> { coin.priceChangePercentage1h }
        PriceChangeInPercentByInterval.DAY -> { coin.priceChangePercentage24h }
        PriceChangeInPercentByInterval.WEEK -> { coin.priceChangePercentage7d }
    }

    Surface(
        modifier = Modifier
            .height(64.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .clickable {
                onCoinSelected(coin.id)
            },
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImage(
                modifier = Modifier.size(48.dp).clip(CircleShape),
                model = coin.imageUrl,
                contentDescription = coin.name
            )
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 4.dp),
                    text = "${ellipsisText(coin.name, 10)} (${coin.symbol.uppercase()})",
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
                if (coin.personalData.isMine) {
                    Text(
                        text = "${coin.personalData.amount} ${coin.symbol.uppercase()}",
                        style = TextStyle(color = LocalContentColor.current.copy(alpha = 0.4f)),
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(verticalArrangement = Arrangement.Center) {
                val formattedCoinPriceChange = formatCoinPriceChange(changeInPercentage = currentChange ?: 0.0)

                Text(modifier = Modifier.align(Alignment.End), text = formatCoinPrice(amount = coin.currentPrice, "usd"))
                Text(
                    modifier = Modifier.align(Alignment.End),
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = formattedCoinPriceChange.color)) { append(formattedCoinPriceChange.formattedChange) }
                    }
                )
            }
        }
    }
}

@Preview(showSystemUi = false, showBackground = false)
@Composable
fun CoinListItemPreview() {
    EcoTheme {
        CoinListItem(
            coin = Coin(
                id = "bitcoin",
                symbol = "btc",
                name = "Bitcoin",
                currentPrice = 74418.0,
                priceChangePercentage24h = 1.8,
                imageUrl = "https://coin-images.coingecko.com/coins/images/1/large/bitcoin.png?1696501400"
            ),
            PriceChangeInPercentByInterval.DAY
        ) {}
    }
}