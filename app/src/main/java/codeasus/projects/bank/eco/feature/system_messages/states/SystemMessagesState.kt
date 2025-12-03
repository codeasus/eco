package codeasus.projects.bank.eco.feature.system_messages.states

import codeasus.projects.bank.eco.core.ui.shared.view.models.SystemMessageUi
import codeasus.projects.bank.eco.domain.local.model.enums.Priority

data class SystemMessagesState (
    val isLoading: Boolean = false,
    val systemMessages: List<SystemMessageUi> = emptyList(),
    val selectedPriority: Priority? = null,
    val error: String? = null
)
