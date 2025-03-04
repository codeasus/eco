package codeasus.projects.bank.eco.core.ui.shared.view.card

import android.graphics.BlurMaskFilter
import android.graphics.Paint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
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
import codeasus.projects.bank.eco.core.ui.shared.view.utils.formatExpiryDate
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme

object BankCardDefaults {
    val WIDTH = 360.dp
    val HEIGHT = 240.dp
    val CORNER_RADIUS = 32.dp
}

@Composable
fun BankCardDark(bankAccount: codeasus.projects.bank.eco.domain.local.model.user.UserBankAccountModel) {
    val colorBackground = Color(31, 23, 80)
    val colorDarker = Color(148, 148, 159, 255)
    val colorLighter = Color.White

    Card(
        modifier = Modifier
            .width(BankCardDefaults.WIDTH)
            .height(BankCardDefaults.HEIGHT),
        shape = RoundedCornerShape(BankCardDefaults.CORNER_RADIUS),
        colors = CardDefaults.cardColors(containerColor = colorBackground)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorBackground)
                .drawBehind {
                    drawIntoCanvas { canvas ->
                        val paint = Paint().apply {
                            maskFilter = BlurMaskFilter(900f, BlurMaskFilter.Blur.NORMAL)
                            color = android.graphics.Color.argb(120, 255, 255, 255)
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
                            color = colorDarker
                        )
                    )
                    Image(
                        modifier = Modifier.width(32.dp),
                        painter = painterResource(R.drawable.ic_contactless_payment),
                        contentScale = ContentScale.FillWidth,
                        colorFilter = ColorFilter.tint(colorDarker),
                        contentDescription = "Contactless Payment Sign",
                    )
                }
                Column {
                    Text(
                        text = bankAccount.name,
                        style = TextStyle(color = colorLighter)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "★★★★  ★★★★  ★★★★  ${bankAccount.number.split(" ").last()}",
                        style = TextStyle(color = colorLighter)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Exp ${formatExpiryDate(bankAccount.expiryDate)}",
                        style = TextStyle(color = colorDarker)
                    )
                    Image(
                        modifier = Modifier.width(36.dp),
                        painter = painterResource(R.drawable.ic_apple_pay),
                        contentScale = ContentScale.FillWidth,
                        colorFilter = ColorFilter.tint(colorDarker),
                        contentDescription = "Contactless Payment Sign",
                    )
                }
            }
        }
    }
}

@Composable
fun BankCardLight(bankAccount: codeasus.projects.bank.eco.domain.local.model.user.UserBankAccountModel) {
    val colorBackground = Color(231, 231, 246, 255)
    val colorDarker = Color(31, 23, 80)
    val colorLighter = Color(66, 24, 119, 255)

    Card(
        modifier = Modifier
            .width(BankCardDefaults.WIDTH)
            .height(BankCardDefaults.HEIGHT),
        shape = RoundedCornerShape(BankCardDefaults.CORNER_RADIUS),
        colors = CardDefaults.cardColors(containerColor = colorBackground)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorBackground)
                .drawBehind {
                    drawIntoCanvas { canvas ->
                        val paint = Paint().apply {
                            maskFilter = BlurMaskFilter(300f, BlurMaskFilter.Blur.NORMAL)
                            color = android.graphics.Color.argb(255, 31, 23, 80)
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
                            color = colorDarker
                        )
                    )
                    Image(
                        modifier = Modifier.width(32.dp),
                        painter = painterResource(R.drawable.ic_contactless_payment),
                        contentScale = ContentScale.FillWidth,
                        colorFilter = ColorFilter.tint(colorDarker),
                        contentDescription = "Contactless Payment Sign",
                    )
                }
                Column {
                    Text(
                        text = bankAccount.name,
                        style = TextStyle(color = colorLighter)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "★★★★  ★★★★  ★★★★  ${bankAccount.number.split(" ").last()}",
                        style = TextStyle(color = colorLighter)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Exp ${formatExpiryDate(bankAccount.expiryDate)}",
                        style = TextStyle(color = colorDarker)
                    )
                    Image(
                        modifier = Modifier.width(36.dp),
                        painter = painterResource(R.drawable.ic_apple_pay),
                        contentScale = ContentScale.FillWidth,
                        colorFilter = ColorFilter.tint(colorDarker),
                        contentDescription = "Contactless Payment Sign",
                    )
                }
            }
        }
    }
}


@Preview(showSystemUi = false, showBackground = false)
@Composable
fun BankCardPreview() {
    EcoTheme {

    }
}