package codeasus.projects.bank.eco.domain.local.repository.system_message

import codeasus.projects.bank.eco.domain.local.model.enums.Priority
import codeasus.projects.bank.eco.domain.local.model.system_message.SystemMessageModel

interface SystemMessageRepository {
    suspend fun insertSystemMessage(systemMessage: SystemMessageModel)
    suspend fun getAllSystemMessages(): List<SystemMessageModel>
    suspend fun getSystemMessagesByPriority(priority: Priority): List<SystemMessageModel>
    suspend fun deleteSystemMessages()
}