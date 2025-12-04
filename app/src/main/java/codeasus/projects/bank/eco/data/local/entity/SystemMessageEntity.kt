package codeasus.projects.bank.eco.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "system_messages")
data class SystemMessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val content: String,
    val priority: Int,
    val createdAt: LocalDateTime,
)