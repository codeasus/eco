package codeasus.projects.bank.eco.feature.system_messages.states

import codeasus.projects.bank.eco.domain.local.model.enums.Priority

sealed class SystemMessagesIntent {
    data object GetAllSystemMessages: SystemMessagesIntent()
    data object DeleteSystemMessages: SystemMessagesIntent()
    data class FilterByPriority(val priority: Priority): SystemMessagesIntent()
}