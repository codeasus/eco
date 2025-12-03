package codeasus.projects.bank.eco.feature.system_messages.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import codeasus.projects.bank.eco.core.ui.shared.mappers.toSystemMessageUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.SystemMessageUi
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme
import codeasus.projects.bank.eco.domain.local.model.enums.Priority
import codeasus.projects.bank.eco.domain.local.model.system_message.SystemMessageModel

@Composable
fun SystemMessageListItem(systemMessage: SystemMessageUi) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp), color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {
                    Text(
                        text = systemMessage.title,
                        style = TextStyle(fontWeight = FontWeight.Bold)
                    )
                    PriorityChip(priority = systemMessage.priority)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(0.6f),
                        text = systemMessage.content,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = TextStyle(fontWeight = FontWeight.Normal)
                    )
                    Text(
                        text = systemMessage.createdAt,
                        style = TextStyle(fontWeight = FontWeight.Normal)
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = false, showBackground = false)
@Composable
fun SystemMessageListItemPreview() {
    EcoTheme {
        SystemMessageListItem(
            SystemMessageModel(
                title = "System Operations",
                content = "This is a system message",
                priority = Priority.HIGH
            ).toSystemMessageUi()
        )
    }
}