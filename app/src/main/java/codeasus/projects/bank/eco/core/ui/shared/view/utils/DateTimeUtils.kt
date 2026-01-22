package codeasus.projects.bank.eco.core.ui.shared.view.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

fun LocalDateTime.isToday(zoneId: ZoneId = ZoneId.systemDefault()): Boolean {
    val today = LocalDate.now(zoneId)
    return this.toLocalDate() == today
}

fun LocalDateTime.isYesterday(zoneId: ZoneId = ZoneId.systemDefault()): Boolean {
    val yesterday = LocalDate.now(zoneId).minusDays(1)
    return this.toLocalDate() == yesterday
}

fun LocalDateTime.isCurrentYear(zoneId: ZoneId = ZoneId.systemDefault()): Boolean {
    val currentYear = LocalDate.now(zoneId).year
    return this.year == currentYear
}