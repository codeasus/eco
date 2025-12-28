package codeasus.projects.bank.eco.feature.home.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import codeasus.projects.bank.eco.R
import codeasus.projects.bank.eco.core.ui.shared.view.utils.ellipsisText
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme

@Composable
fun TemplatePaymentComponent(templatePayment: TemplatePayment) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(2.dp)) {
        IconButton(
            colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.9F)),
            onClick = {}
        ) {
            Icon(
                painter = painterResource(templatePayment.icon),
                contentDescription = templatePayment.description
            )
        }
        Text(ellipsisText(templatePayment.name), style = TextStyle(fontSize = 14.sp))
    }
}

@Preview(showSystemUi = false, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun TemplatePaymentComponentLightPreview() {
    EcoTheme {
        TemplatePaymentComponent(TemplatePayment(0, "Internet", "Internet Payment", R.drawable.ic_internet))
    }
}

@Preview(showSystemUi = false, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TemplatePaymentComponentDarkPreview() {
    EcoTheme {
        TemplatePaymentComponent(TemplatePayment(0, "Internet", "Internet Payment", R.drawable.ic_internet))
    }
}