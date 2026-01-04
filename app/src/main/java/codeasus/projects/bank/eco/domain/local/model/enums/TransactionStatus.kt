package codeasus.projects.bank.eco.domain.local.model.enums

enum class TransactionStatus(val label: String) {
    PENDING("Pending"),
    COMPLETED("Completed"),
    FAILED("Failed"),
    CANCELED("Canceled")
}