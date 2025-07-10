package codeasus.projects.bank.eco.domain.local.model.system_message

import codeasus.projects.bank.eco.domain.local.model.enums.Priority
import java.time.LocalDateTime

data class SystemMessageModel(
    val title: String,
    val content: String,
    val priority: Priority,
    val createdAt: LocalDateTime = LocalDateTime.now(),
)