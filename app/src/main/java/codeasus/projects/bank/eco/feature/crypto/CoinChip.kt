package codeasus.projects.bank.eco.feature.crypto

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import codeasus.projects.bank.eco.domain.remote.model.crypto.Coin

@Composable
fun CoinChip(coin: Coin, isSelected: Boolean, onClick: () -> Unit) {
    val chipShape = RoundedCornerShape(16.dp)
    val mainColor = Color.White
    Text(
        modifier = Modifier
            .clip(chipShape)
            .clickable { onClick() }
            .background(
                color = if (isSelected) mainColor.copy(alpha = 0.3F)
                else Color.Transparent
            )
            .border(1.dp, mainColor, chipShape)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        text = coin.name,
        style = TextStyle(color = mainColor),
    )
}