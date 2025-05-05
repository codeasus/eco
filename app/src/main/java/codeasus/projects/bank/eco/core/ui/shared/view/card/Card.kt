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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
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
fun BankCardFront(
    modifier: Modifier,
    bankAccount: UserBankAccountModel,
    rotationYAngle: Float = 0f,
    bankCardAlpha: Float = 1.0F,
    onSelected: (UserBankAccountModel) -> Unit = {}
) {
    val themeColors = when (bankAccount.type) {
        BankAccountType.NORMAL -> BankCardDefaults.NormalCardColors
        BankAccountType.PLATINUM -> BankCardDefaults.PlatinumCardColors
    }

    Card(
        modifier = modifier
            .graphicsLayer {
                alpha = bankCardAlpha
                rotationY = rotationYAngle
                cameraDistance = 12f * density
            }
            .clip(RoundedCornerShape(BankCardDefaults.CORNER_RADIUS.dp))
            .clickable { onSelected(bankAccount) },
        shape = RoundedCornerShape(BankCardDefaults.CORNER_RADIUS.dp),
        colors = CardDefaults.cardColors(containerColor = themeColors.colorBackground)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = themeColors.colorBackground)
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
                            color = themeColors.colorTextDarker
                        )
                    )
                    Image(
                        modifier = Modifier.width(32.dp),
                        painter = painterResource(R.drawable.ic_contactless_payment),
                        contentScale = ContentScale.FillWidth,
                        colorFilter = ColorFilter.tint(themeColors.colorTextDarker),
                        contentDescription = "Contactless Payment Sign",
                    )
                }
                Column {
                    Text(
                        text = bankAccount.name,
                        style = TextStyle(color = themeColors.colorTextLighter)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "★★★★  ★★★★  ★★★★  ${bankAccount.number.split(" ").last()}",
                            style = TextStyle(color = themeColors.colorTextLighter)
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Exp ${formatExpiryDate(bankAccount.expiryDate)}",
                        style = TextStyle(color = themeColors.colorTextDarker)
                    )
                    Image(
                        modifier = Modifier.width(36.dp),
                        painter = painterResource(R.drawable.ic_apple_pay),
                        contentScale = ContentScale.FillWidth,
                        colorFilter = ColorFilter.tint(themeColors.colorTextDarker),
                        contentDescription = "Contactless Payment Sign",
                    )
                }
            }
        }
    }
}

@Composable
fun BankCardBack(modifier: Modifier, bankAccount: UserBankAccountModel, rotationYAngle: Float = 0f) {

    val themeColors = when (bankAccount.type) {
        BankAccountType.NORMAL -> BankCardDefaults.NormalCardColors
        BankAccountType.PLATINUM -> BankCardDefaults.PlatinumCardColors
    }

    Card(
        modifier = modifier
            .graphicsLayer {
                rotationY = rotationYAngle
                cameraDistance = 12f * density
            }
            .clip(RoundedCornerShape(BankCardDefaults.CORNER_RADIUS.dp)),
        shape = RoundedCornerShape(BankCardDefaults.CORNER_RADIUS.dp),
        colors = CardDefaults.cardColors(containerColor = themeColors.colorBackground)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = themeColors.colorBackground)
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
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(Modifier.height(24.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(Color.Black)
                ) {
                }
                Spacer(Modifier.height(12.dp))
                Row(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth()
                        .height(36.dp)
                        .background(Color.White)
                        .padding(horizontal = 24.dp).graphicsLayer {
                            if(rotationYAngle >= 90.0) {
                                rotationY = 180.0f
                            }
                        },
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "**${bankAccount.ccv[2]}",
                        style = TextStyle(color = themeColors.colorTextDarker)
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = false, showBackground = false)
@Composable
fun BankCardDarkPreview() {
    EcoTheme {
        BankCardFront(
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
        BankCardFront(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp),
            bankAccount = DataSourceDefaults.unknownUser.bankAccounts[1],
            bankCardAlpha = 1.0f
        ) {

        }
    }
}

@Preview(showSystemUi = false, showBackground = false)
@Composable
fun BankCardBackLightPreview() {
    EcoTheme {
        BankCardBack(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp),
            bankAccount = DataSourceDefaults.unknownUser.bankAccounts[1],
        )
    }
}