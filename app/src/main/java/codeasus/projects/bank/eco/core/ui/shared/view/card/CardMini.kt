package codeasus.projects.bank.eco.core.ui.shared.view.card

import android.graphics.BlurMaskFilter
import android.graphics.Paint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import codeasus.projects.bank.eco.R
import codeasus.projects.bank.eco.core.ui.shared.view.models.BankAccountUi
import codeasus.projects.bank.eco.core.ui.shared.view.utils.DataSourceDefaults
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme
import codeasus.projects.bank.eco.domain.local.model.enums.BankAccountScheme
import codeasus.projects.bank.eco.domain.local.model.enums.BankAccountType

@Composable
fun CardMini(modifier: Modifier, bankAccountUi: BankAccountUi, rotation: Float) {
    val themeColors = when (bankAccountUi.type) {
        BankAccountType.PERSONAL -> BankCardDefaults.NormalCardColors
        BankAccountType.PLATINUM -> BankCardDefaults.PlatinumCardColors
        BankAccountType.UNKNOWN -> BankCardDefaults.UnknownCardColors
    }

    Card(
        modifier = modifier
            .rotate(rotation)
            .clip(RoundedCornerShape(BankCardDefaults.MINI_CORNER_RADIUS.dp)),
        shape = RoundedCornerShape(BankCardDefaults.MINI_CORNER_RADIUS.dp),
        colors = CardDefaults.cardColors(containerColor = themeColors.colorBackground)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = themeColors.colorBackground)
                .drawBehind {
                    drawIntoCanvas { canvas ->
                        val paint = Paint().apply {
                            maskFilter = BlurMaskFilter(55F, BlurMaskFilter.Blur.NORMAL)
                            color = themeColors.colorBackgroundShape
                        }
                        canvas.nativeCanvas.drawCircle(center.x + 12F, center.y, 30F, paint)
                        canvas.nativeCanvas.drawCircle(center.x - 80F, center.y, 25F, paint)
                        canvas.nativeCanvas.drawCircle(0F + 50F, 0F, 30F, paint)
                    }
                }
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Eco",
                        style = TextStyle(
                            fontWeight = FontWeight.Medium,
                            color = themeColors.colorTextDarker,
                            fontSize = 8.sp
                        )
                    )
                    Image(
                        modifier = Modifier.width(12.dp),
                        painter = painterResource(R.drawable.ic_contactless_payment),
                        contentScale = ContentScale.FillWidth,
                        colorFilter = ColorFilter.tint(themeColors.colorTextDarker),
                        contentDescription = "Contactless Payment Sign",
                    )
                }
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = bankAccountUi.name,
                        style = TextStyle(
                            color = themeColors.colorTextLighter,
                            fontSize = 4.sp
                        )
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = bankAccountUi.number,
                            style = TextStyle(
                                color = themeColors.colorTextLighter,
                                fontSize = 4.sp
                            )
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Exp ${bankAccountUi.expiryDate}",
                        style = TextStyle(
                            color = themeColors.colorTextDarker,
                            fontSize = 4.sp
                        )
                    )
                    val scheme = when (bankAccountUi.scheme) {
                        BankAccountScheme.VISA -> R.drawable.ic_visa_outlined
                        BankAccountScheme.MASTERCARD -> R.drawable.ic_master_outlined
                        BankAccountScheme.AMERICAN_EXPRESS -> R.drawable.ic_amex_outlined
                        else -> 0
                    }
                    Image(
                        modifier = Modifier.width(12.dp),
                        painter = painterResource(scheme),
                        contentScale = ContentScale.FillWidth,
                        colorFilter = ColorFilter.tint(themeColors.colorTextDarker),
                        contentDescription = "Contactless Payment Sign",
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = false, showBackground = false)
@Composable
fun CardMiniDarkPreview() {
    EcoTheme {
        CardMini(
            modifier = Modifier
                .width(128.dp)
                .height(86.dp),
            rotation = 0F,
            bankAccountUi = DataSourceDefaults.unknownUser.second[1]
        )
    }
}

@Preview(showSystemUi = false, showBackground = false)
@Composable
fun CardMiniLightPreview() {
    EcoTheme {
        CardMini(
            modifier = Modifier
                .width(128.dp)
                .height(72.dp),
            rotation = 0F,
            bankAccountUi = DataSourceDefaults.unknownUser.second[0]
        )
    }
}