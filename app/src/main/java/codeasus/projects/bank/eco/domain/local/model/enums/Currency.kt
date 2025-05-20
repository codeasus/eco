package codeasus.projects.bank.eco.domain.local.model.enums

import androidx.annotation.DrawableRes
import codeasus.projects.bank.eco.R

enum class Currency(val symbol: String, val code: String, @DrawableRes val icon: Int) {
    EUR("€", "EUR", R.drawable.ic_eur),
    USD("$", "USD", R.drawable.ic_usd),
    PLN("zł", "PLN", R.drawable.ic_pln);
}