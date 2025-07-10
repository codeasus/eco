package codeasus.projects.bank.eco.feature.system_messages.presentation.utils

import androidx.compose.ui.graphics.Color
import codeasus.projects.bank.eco.domain.local.model.enums.Priority

fun getColorBasedOnSystemMessagePriority(priority: Priority): Color {
    return when (priority) {
        Priority.LOW -> SystemMessageUIItemPriorityColors.LOW
        Priority.MEDIUM -> SystemMessageUIItemPriorityColors.MEDIUM
        Priority.HIGH -> SystemMessageUIItemPriorityColors.HIGH
    }
}