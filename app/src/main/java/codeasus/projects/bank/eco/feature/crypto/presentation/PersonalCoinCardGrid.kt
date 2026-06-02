package codeasus.projects.bank.eco.feature.crypto.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import codeasus.projects.bank.eco.domain.remote.model.crypto.Coin
import codeasus.projects.bank.eco.feature.crypto.presentation.states.PersonalCoinCard

@Composable
fun PersonalCoinCardGrid(
    modifier: Modifier = Modifier,
    coins: List<Coin>,
    priceChangeInPercentByInterval: PriceChangeInPercentByInterval,
    onCoinSelected: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(
            items = coins,
            key = { _, coin -> coin.id }
        ) { index, coin ->
            PersonalCoinCard(
                coin = coin,
                priceChangeInPercentByInterval = priceChangeInPercentByInterval,
                coinPriceHistory = coins[index].priceFluctuationHistory,
                onCoinSelected = onCoinSelected
            )
        }
    }
}