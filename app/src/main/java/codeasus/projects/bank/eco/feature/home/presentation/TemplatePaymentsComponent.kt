package codeasus.projects.bank.eco.feature.home.presentation

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import codeasus.projects.bank.eco.R
import codeasus.projects.bank.eco.core.ui.shared.view.TextButton
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme

@Composable
fun TemplatePaymentsComponent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4F))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Templates")
            TextButton("View all") { }
        }
        Row(
            modifier = Modifier.padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(36.dp)
        ) {
            TemplatePaymentComponent(TemplatePayment(-1, "Add new", "Add a new template", R.drawable.ic_topup))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                items(count = TemplatePaymentsItems.value.take(8).size) {
                    TemplatePaymentComponent(TemplatePaymentsItems.value[it])
                }
            }
        }
    }
}

@Preview(showSystemUi = false, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun TemplatePaymentsComponentLightPreview() {
    EcoTheme {
        TemplatePaymentsComponent()
    }
}

@Preview(showSystemUi = false, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TemplatePaymentsComponentDarkPreview() {
    EcoTheme {
        TemplatePaymentsComponent()
    }
}