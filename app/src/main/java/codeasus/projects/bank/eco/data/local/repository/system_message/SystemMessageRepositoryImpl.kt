package codeasus.projects.bank.eco.data.local.repository.system_message

import codeasus.projects.bank.eco.data.local.dao.SystemMessageDao
import codeasus.projects.bank.eco.data.local.mappers.toSystemMessageEntity

import codeasus.projects.bank.eco.data.local.mappers.toSystemMessageModel
import codeasus.projects.bank.eco.domain.local.model.enums.Priority
import codeasus.projects.bank.eco.domain.local.model.system_message.SystemMessageModel
import codeasus.projects.bank.eco.domain.local.repository.system_message.SystemMessageRepository
import javax.inject.Inject

class SystemMessageRepositoryImpl @Inject constructor(private val systemMessageDao: SystemMessageDao): SystemMessageRepository {
    override suspend fun insertSystemMessage(systemMessage: SystemMessageModel) {
        systemMessageDao.insertSystemMessage(systemMessage.toSystemMessageEntity())
    }

    override suspend fun getAllSystemMessages(): List<SystemMessageModel> {
        return systemMessageDao.getAllSystemMessages().map { it.toSystemMessageModel() }
    }

    override suspend fun getSystemMessagesByPriority(priority: Priority): List<SystemMessageModel> {
        return systemMessageDao.getSystemMessagesByPriority(priority.value).map { it.toSystemMessageModel() }
    }

    override suspend fun deleteSystemMessages() {
        systemMessageDao.deleteSystemMessages()
    }
}