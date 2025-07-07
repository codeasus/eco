package codeasus.projects.bank.eco.domain.remote.model.banking

data class BinLookupModel(
    val status: String? = null,
    val scheme: String? = null,
    val type: String? = null,
    val issuer: String? = null,
    val cardTier: String? = null,
    val country: Country? = null,
    val luhn: Boolean? = false
)