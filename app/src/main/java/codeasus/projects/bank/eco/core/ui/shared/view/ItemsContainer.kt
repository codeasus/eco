package codeasus.projects.bank.eco.core.ui.shared.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun ItemsContainer(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(color = MaterialTheme.colorScheme.surfaceVariant)
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        content.invoke()
    }
}