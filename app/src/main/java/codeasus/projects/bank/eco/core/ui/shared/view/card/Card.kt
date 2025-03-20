package codeasus.projects.bank.eco.core.ui.shared.view.card

import android.graphics.BlurMaskFilter
import android.graphics.Paint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import codeasus.projects.bank.eco.R
import codeasus.projects.bank.eco.core.ui.shared.view.utils.DataSourceDefaults
import codeasus.projects.bank.eco.core.ui.shared.view.utils.formatExpiryDate
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme
import codeasus.projects.bank.eco.domain.local.model.enums.BankAccountType
import codeasus.projects.bank.eco.domain.local.model.user.UserBankAccountModel

@Composable
fun BankCard(
    modifier: Modifier,
    bankAccount: UserBankAccountModel,
    bankCardAlpha: Float,
    onCardSelected: (UserBankAccountModel) -> Unit
) {
    val themeColors = when(bankAccount.type) {
        BankAccountType.NORMAL ->  BankCardDefaults.NormalCardColors
        BankAccountType.PLATINUM -> BankCardDefaults.PlatinumCardColors
    }

    Box(contentAlignment = Alignment.Center) {
        Card(
            modifier = modifier
                .clip(RoundedCornerShape(BankCardDefaults.CORNER_RADIUS.dp))
                .clickable { onCardSelected(bankAccount) },
            shape = RoundedCornerShape(BankCardDefaults.CORNER_RADIUS.dp),
            colors = CardDefaults.cardColors(containerColor = themeColors.colorBackground.copy(alpha = bankCardAlpha))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = themeColors.colorBackground.copy(alpha = bankCardAlpha))
                    .drawBehind {
                        drawIntoCanvas { canvas ->
                            val paint = Paint().apply {
                                maskFilter = BlurMaskFilter(900f, BlurMaskFilter.Blur.NORMAL)
                                color = themeColors.colorBackgroundShape
                            }
                            canvas.nativeCanvas.drawCircle(center.x + 50, center.y, 120f, paint)
                            canvas.nativeCanvas.drawCircle(center.x - 250, center.y, 100f, paint)
                            canvas.nativeCanvas.drawCircle(0f + 200, 0f, 120f, paint)
                        }
                    }
                    .padding(24.dp)
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
                            text = "Echo",
                            style = TextStyle(
                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                fontWeight = FontWeight.Medium,
                                color = themeColors.colorTextDarker.copy(alpha = bankCardAlpha)
                            )
                        )
                        Image(
                            modifier = Modifier.width(32.dp),
                            painter = painterResource(R.drawable.ic_contactless_payment),
                            contentScale = ContentScale.FillWidth,
                            colorFilter = ColorFilter.tint(themeColors.colorTextDarker.copy(alpha = bankCardAlpha)),
                            contentDescription = "Contactless Payment Sign",
                        )
                    }
                    Column {
                        Text(
                            text = bankAccount.name,
                            style = TextStyle(color = themeColors.colorTextLighter.copy(alpha = bankCardAlpha))
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "★★★★  ★★★★  ★★★★  ${bankAccount.number.split(" ").last()}",
                            style = TextStyle(color = themeColors.colorTextLighter.copy(alpha = bankCardAlpha))
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Exp ${formatExpiryDate(bankAccount.expiryDate)}",
                            style = TextStyle(color = themeColors.colorTextDarker.copy(alpha = bankCardAlpha))
                        )
                        Image(
                            modifier = Modifier.width(36.dp),
                            painter = painterResource(R.drawable.ic_apple_pay),
                            contentScale = ContentScale.FillWidth,
                            colorFilter = ColorFilter.tint(themeColors.colorTextDarker.copy(alpha = bankCardAlpha)),
                            contentDescription = "Contactless Payment Sign",
                        )
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = false, showBackground = false)
@Composable
fun BankCardDarkPreview() {
    EcoTheme {
        BankCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp),
            bankAccount = DataSourceDefaults.unknownUser.bankAccounts[0],
            bankCardAlpha = 1.0f
        ) {

        }
    }
}

@Preview(showSystemUi = false, showBackground = false)
@Composable
fun BankCardLightPreview() {
    EcoTheme {
        BankCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp),
            bankAccount = DataSourceDefaults.unknownUser.bankAccounts[1],
            bankCardAlpha = 1.0f
        ) {

        }
    }
}