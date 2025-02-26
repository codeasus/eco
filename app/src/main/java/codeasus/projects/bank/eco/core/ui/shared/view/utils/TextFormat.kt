package codeasus.projects.bank.eco.core.ui.shared.view.utils

import androidx.compose.ui.graphics.Color
import codeasus.projects.bank.eco.core.ui.shared.view.transaction.TransactionUIItemColors
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatLocalDateTime(dateTime: LocalDateTime): String {
    val pattern = "dd MMM, HH:mm"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH)
    return dateTime.format(formatter)
}

fun formatExpiryDate(expiryDate: LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("MM/yy")
    return expiryDate.format(formatter)
}

fun defineTransactionAmountColor(transactionType: codeasus.projects.bank.eco.domain.local.model.enums.TransactionType): Color {
    return when (transactionType) {
        codeasus.projects.bank.eco.domain.local.model.enums.TransactionType.TRANSFER, codeasus.projects.bank.eco.domain.local.model.enums.TransactionType.WITHDRAWAL, codeasus.projects.bank.eco.domain.local.model.enums.TransactionType.PAYMENT -> TransactionUIItemColors.COLOR_MATERIAL_RED
        codeasus.projects.bank.eco.domain.local.model.enums.TransactionType.DEPOSIT, codeasus.projects.bank.eco.domain.local.model.enums.TransactionType.REFUND -> Color.Unspecified
    }
}

fun formatUITransactionAmount(transaction: codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel): String {
    fun formatForSign(sign: String) = "$sign ${transaction.currency.symbol}${formatTransactionAmount(transaction.amount)}"
    return when (transaction.type) {
        codeasus.projects.bank.eco.domain.local.model.enums.TransactionType.TRANSFER -> formatForSign("-")
        codeasus.projects.bank.eco.domain.local.model.enums.TransactionType.DEPOSIT -> formatForSign("+")
        codeasus.projects.bank.eco.domain.local.model.enums.TransactionType.WITHDRAWAL -> formatForSign("-")
        codeasus.projects.bank.eco.domain.local.model.enums.TransactionType.PAYMENT -> formatForSign("-")
        codeasus.projects.bank.eco.domain.local.model.enums.TransactionType.REFUND -> formatForSign("+")
    }
}

fun formatTransactionAmount(amount: Double): String {
    val format = DecimalFormat("#,##0.00", DecimalFormatSymbols(Locale.ENGLISH))
    return format.format(amount)
}

fun formatTransactionRate(amount: Double): String {
    val format = DecimalFormat("#,#0.0", DecimalFormatSymbols(Locale.ENGLISH))
    return format.format(amount)
}