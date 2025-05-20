package codeasus.projects.bank.eco.feature.transfer.utils

import codeasus.projects.bank.eco.R

enum class Currency {
    EUR, USD, PLN
}

object Currencies {
    val items = mapOf(
        "EUR" to R.drawable.ic_eur,
        "USD" to R.drawable.ic_usd,
        "PLN" to R.drawable.ic_pln
    )

    fun getIcon(currencyName: String): Int {
        return items[currencyName] ?: R.drawable.ic_question
    }
}