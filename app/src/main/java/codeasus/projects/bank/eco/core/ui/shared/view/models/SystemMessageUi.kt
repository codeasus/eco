package codeasus.projects.bank.eco.core.ui.shared.view.models

import codeasus.projects.bank.eco.domain.local.model.enums.Priority

data class SystemMessageUi(
    val title: String,
    val content: String,
    val priority: Priority,
    val createdAt: String
)
