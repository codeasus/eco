package codeasus.projects.bank.eco.domain.local.model.enums

enum class TransactionType(val label: String) {
    DEPOSIT("Deposit"),
    WITHDRAWAL("Withdrawal"),
    TRANSFER("Transfer"),
    PAYMENT("Payment"),
    REFUND("Refund")
}