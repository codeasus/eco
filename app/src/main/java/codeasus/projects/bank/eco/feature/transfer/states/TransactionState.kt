package codeasus.projects.bank.eco.feature.transfer.states

data class TransactionState(
    val accountName: String = "",
    val accountNumber: String = "",
    var amount: Double = 0.0,
    val currency: String = "EUR"
)