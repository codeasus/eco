package codeasus.projects.bank.eco.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import codeasus.projects.bank.eco.data.local.entity.SystemMessageEntity

@Dao
interface SystemMessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSystemMessage(systemMessage: SystemMessageEntity)

    @Query("SELECT * FROM system_messages")
    suspend fun getAllSystemMessages(): List<SystemMessageEntity>

    @Query("SELECT * FROM system_messages WHERE priority = :priority ORDER BY createdAt DESC")
    suspend fun getSystemMessagesByPriority(priority: Int): List<SystemMessageEntity>

    @Query("DELETE FROM system_messages")
    suspend fun deleteSystemMessages()
}