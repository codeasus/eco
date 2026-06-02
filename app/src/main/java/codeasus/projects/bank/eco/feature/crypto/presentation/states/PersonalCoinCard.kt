package codeasus.projects.bank.eco.feature.crypto.presentation.states

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
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
import codeasus.projects.bank.eco.domain.remote.model.crypto.Coin
import codeasus.projects.bank.eco.domain.remote.model.crypto.CoinPersonalAttributionData
import codeasus.projects.bank.eco.domain.remote.model.crypto.PricePoint
import codeasus.projects.bank.eco.feature.crypto.presentation.CoinLineChart
import codeasus.projects.bank.eco.feature.crypto.presentation.PriceChangeInPercentByInterval
import coil.compose.AsyncImage

fun Modifier.cardBackground(priceChangeInPercent: Double?): Modifier {

    if(priceChangeInPercent == null) return this

    return this.drawBehind {
        val colorPercentageProfit = Color(87, 206, 74, 255)
        val colorPercentageLoss = Color(190, 78, 78, 255)

        val baseColor = if (priceChangeInPercent >= 0.0) {
            colorPercentageProfit
        } else {
            colorPercentageLoss
        }

        val gradient = Brush.linearGradient(
            colorStops = arrayOf(
                0.0f to baseColor.copy(alpha = 0.8f),
                0.12f to baseColor.copy(alpha = 0.3f),
                0.20f to Color.Transparent
            ),
            start = Offset(size.width, 0f),
            end = Offset(0f, size.height)
        )
        drawRect(brush = gradient)
    }
}

@Composable
fun PersonalCoinCard(
    coin: Coin,
    priceChangeInPercentByInterval: PriceChangeInPercentByInterval,
    coinPriceHistory: List<PricePoint>,
    onCoinSelected: (String) -> Unit
) {

    val currentChange = when (priceChangeInPercentByInterval) {
        PriceChangeInPercentByInterval.HOUR -> {
            coin.priceChangePercentage1h
        }

        PriceChangeInPercentByInterval.DAY -> {
            coin.priceChangePercentage24h
        }

        PriceChangeInPercentByInterval.WEEK -> {
            coin.priceChangePercentage7d
        }
    }

    Card(
        modifier = Modifier
            .size(132.dp)
            .clickable { onCoinSelected(coin.id) },
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(24.dp))
                .cardBackground(currentChange)
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape),
                        model = coin.imageUrl,
                        contentDescription = coin.name
                    )
                    Text(
                        modifier = Modifier.padding(bottom = 4.dp),
                        text = "${ellipsisText(coin.name, 10)} (${coin.symbol.uppercase()})",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = MaterialTheme.typography.titleSmall.fontSize
                        )
                    )
                }
                val formattedCoinPriceChange =
                    formatCoinPriceChange(changeInPercentage = currentChange ?: 0.0)

                if (coin.personalData.isMine) {
                    Text(
                        text = "${coin.personalData.amount} ${coin.symbol.uppercase()}",
                        style = TextStyle(
                            color = LocalContentColor.current.copy(alpha = 0.4f),
                            fontSize = MaterialTheme.typography.bodySmall.fontSize
                        ),
                    )
                }

                Text(
                    text = buildAnnotatedString {
                        append(formatCoinPrice(amount = coin.currentPrice, "usd"))
                        append("  ")
                        withStyle(style = SpanStyle(color = formattedCoinPriceChange.color)) {
                            append(
                                formattedCoinPriceChange.formattedChange
                            )
                        }
                    },
                    style = TextStyle(fontSize = MaterialTheme.typography.bodySmall.fontSize)
                )

                CoinLineChart(
                    modifier = Modifier.weight(1.0F),
                    data = coinPriceHistory,
                    timeframe = priceChangeInPercentByInterval.toChartTimeframe(),
                    showAxis = false
                )
            }
        }
    }
}

@Preview(showSystemUi = false, showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PersonalCoinCardPreviewDark() {
    MaterialTheme {
        val previewCoinPriceHistory = run {
            val now = System.currentTimeMillis()
            List(24) { i ->
                PricePoint(
                    timestamp = now - (23 - i) * 3_600_000L,
                    price = 45_000.0 + (Math.random() - 0.5) * 2_000.0
                )
            }
        }

        PersonalCoinCard(
            coin = Coin(
                id = "bitcoin",
                symbol = "btc",
                name = "Bitcoin",
                currentPrice = 74418.0,
                priceChangePercentage24h = 1.8,
                personalData = CoinPersonalAttributionData(amount = 1.2, isMine = true),
                imageUrl = "https://coin-images.coingecko.com/coins/images/1/large/bitcoin.png?1696501400"
            ),
            priceChangeInPercentByInterval = PriceChangeInPercentByInterval.DAY,
            coinPriceHistory = previewCoinPriceHistory,
        ) {

        }
    }
}

@Preview(showSystemUi = false, showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PersonalCoinCardPreviewLight() {
    MaterialTheme {
        val previewCoinPriceHistory = run {
            val now = System.currentTimeMillis()
            List(24) { i ->
                PricePoint(
                    timestamp = now - (23 - i) * 3_600_000L,
                    price = 45_000.0 + (Math.random() - 0.5) * 2_000.0
                )
            }
        }

        PersonalCoinCard(
            coin = Coin(
                id = "bitcoin",
                symbol = "btc",
                name = "Bitcoin",
                currentPrice = 74418.0,
                priceChangePercentage24h = -1.8,
                personalData = CoinPersonalAttributionData(amount = 1.2, isMine = true),
                imageUrl = "https://coin-images.coingecko.com/coins/images/1/large/bitcoin.png?1696501400"
            ),
            priceChangeInPercentByInterval = PriceChangeInPercentByInterval.DAY,
            coinPriceHistory = previewCoinPriceHistory,
        ) {

        }
    }
}