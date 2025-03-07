package codeasus.projects.bank.eco.domain.local.model.enums

enum class TransactionType {
    DEPOSIT, WITHDRAWAL, TRANSFER, PAYMENT, REFUND;

    companion object {
        fun toDefaultSelectionMap(): Map<TransactionType, Boolean> =
            entries.associateWith { false }
    }
}

