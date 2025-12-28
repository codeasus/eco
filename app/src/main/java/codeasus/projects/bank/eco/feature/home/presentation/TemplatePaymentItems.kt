package codeasus.projects.bank.eco.feature.home.presentation

import androidx.annotation.DrawableRes
import codeasus.projects.bank.eco.R

data class TemplatePayment(
    val id: Long,
    val name: String,
    val description: String,
    @param:DrawableRes val icon: Int
)

object TemplatePaymentsItems {
    val value = listOf(
        TemplatePayment(0, "Internet", "Internet Payment", R.drawable.ic_internet),
        TemplatePayment(1, "Electricity", "Electricity Payment", R.drawable.ic_electricity),
        TemplatePayment(2, "Rent", "Rent Payment", R.drawable.ic_rent)
    )
}