package codeasus.projects.bank.eco.domain.local.model.enums

enum class BankAccountType {
    PERSONAL, PLATINUM, UNKNOWN;

    override fun toString(): String {
        return this.name.lowercase()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }
}