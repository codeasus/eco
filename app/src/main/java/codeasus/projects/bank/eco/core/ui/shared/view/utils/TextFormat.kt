package codeasus.projects.bank.eco.core.ui.shared.view.utils

import androidx.compose.ui.graphics.Color
import codeasus.projects.bank.eco.core.ui.shared.view.transaction.TransactionUIItemColors
import codeasus.projects.bank.eco.domain.local.model.enums.TransactionType
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

fun formatCardNumber(cardNumber: String): String {
    if (cardNumber.length >= 16) throw IllegalArgumentException("Card number should not have more than 16 digits")
    val builder = StringBuilder()
    for (i in cardNumber.indices) {
        if (i % 4 == 0 && i != 0) builder.append(" ")
        builder.append(cardNumber[i])
    }
    return builder.toString()
}

fun defineTransactionAmountColor(transactionType: TransactionType): Color {
    return when (transactionType) {
        TransactionType.TRANSFER, TransactionType.WITHDRAWAL, TransactionType.PAYMENT -> TransactionUIItemColors.COLOR_MATERIAL_RED
        TransactionType.DEPOSIT, TransactionType.REFUND -> Color.Unspecified
    }
}

fun formatUITransactionAmount(transaction: codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel): String {
    fun formatForSign(sign: String) = "$sign ${transaction.currency.symbol}${formatTransactionAmount(transaction.amount)}"
    return when (transaction.type) {
        TransactionType.DEPOSIT, TransactionType.REFUND -> formatForSign("+")
        TransactionType.TRANSFER, TransactionType.WITHDRAWAL, TransactionType.PAYMENT -> formatForSign("-")
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