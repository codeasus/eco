package codeasus.projects.bank.eco.domain.remote.model.banking

data class BinLookupModel(
    val status: String?,
    val scheme: String?,
    val type: String?,
    val issuer: String?,
    val cardTier: String?,
    val country: Country?,
    val luhn: Boolean?
)