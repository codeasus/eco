package codeasus.projects.bank.eco.feature.system_messages.states

import codeasus.projects.bank.eco.domain.local.model.enums.Priority
import codeasus.projects.bank.eco.domain.local.model.system_message.SystemMessageModel

data class SystemMessagesState (
    val isLoading: Boolean = false,
    val systemMessages: List<SystemMessageModel> = emptyList(),
    val selectedPriority: Priority? = null,
    val error: String? = null
)
