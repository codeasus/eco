package codeasus.projects.bank.eco.feature.system_messages.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import codeasus.projects.bank.eco.domain.local.model.enums.Priority
import codeasus.projects.bank.eco.feature.system_messages.presentation.utils.getColorBasedOnSystemMessagePriority

@Composable
fun PriorityChip(priority: Priority) {
    val color = getColorBasedOnSystemMessagePriority(priority)
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = color,
        tonalElevation = 2.dp
    ) {
        Text(
            text = priority.label,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            color = Color.White,
            style = MaterialTheme.typography.labelSmall
        )
    }
}