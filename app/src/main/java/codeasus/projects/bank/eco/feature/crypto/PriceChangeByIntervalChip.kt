package codeasus.projects.bank.eco.feature.crypto

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme
import codeasus.projects.bank.eco.feature.crypto.presentation.PriceChangeInPercentByInterval

@Composable
fun PriceChangeByIntervalChip(priceChangeInPercentByInterval: PriceChangeInPercentByInterval, isSelected: Boolean, onClick: () -> Unit) {
    val chipShape = RoundedCornerShape(12.dp)
    val mainColor = MaterialTheme.colorScheme.primary

    Text(
        modifier = Modifier
            .clip(chipShape)
            .clickable { onClick() }
            .background(
                color = if (isSelected) mainColor.copy(alpha = 0.3F)
                else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.9F)
            )
            .then(
                other = if (isSelected) Modifier.border(1.dp, mainColor, chipShape)
                else Modifier
            )
            .padding(horizontal = 12.dp, vertical = 4.dp),
        text = priceChangeInPercentByInterval.text,
        style = TextStyle(color = mainColor),
    )
}

@Composable
fun PriceChangeByIntervalChipVariant(priceChangeInPercentByInterval: PriceChangeInPercentByInterval, isSelected: Boolean, onClick: () -> Unit) {
    val chipShape = RoundedCornerShape(12.dp)
    val mainColor = MaterialTheme.colorScheme.primary

    Text(
        modifier = Modifier
            .clip(chipShape)
            .clickable { onClick() }
            .background(
                color = if (isSelected) mainColor.copy(alpha = 0.3F)
                else Color.Transparent
            )
            .border(1.dp, mainColor, chipShape)
            .padding(horizontal = 16.dp, vertical = 4.dp),
        text = priceChangeInPercentByInterval.text,
        style = TextStyle(color = mainColor),
    )
}

@Preview(
    showSystemUi = false,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PriceChangeByIntervalChipPreview() {
    EcoTheme {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            PriceChangeByIntervalChip(
                priceChangeInPercentByInterval = PriceChangeInPercentByInterval.DAY,
                isSelected = true
            ) {

            }

            PriceChangeByIntervalChip(
                priceChangeInPercentByInterval = PriceChangeInPercentByInterval.DAY,
                isSelected = false
            ) {

            }

            PriceChangeByIntervalChipVariant(
                priceChangeInPercentByInterval = PriceChangeInPercentByInterval.DAY,
                isSelected = true
            ) {

            }

            PriceChangeByIntervalChipVariant(
                priceChangeInPercentByInterval = PriceChangeInPercentByInterval.DAY,
                isSelected = false
            ) {

            }
        }
    }
}