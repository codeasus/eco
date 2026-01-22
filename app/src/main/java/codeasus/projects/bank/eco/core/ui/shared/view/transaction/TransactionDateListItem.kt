package codeasus.projects.bank.eco.core.ui.shared.view.transaction

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme

@Composable
fun TransactionDateListItem(date: String) {
    Surface(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        color = Color.Transparent
    ) {
        Text(text = date)
    }
}

@Preview(showSystemUi = false, showBackground = false)
@Composable
fun TransactionDateListItemPreview() {
    EcoTheme {
        TransactionDateListItem("September 16")
    }
}