package codeasus.projects.bank.eco.domain.local.model.enums

enum class Priority(val value: Int) {
    LOW(0),
    MEDIUM(1),
    HIGH(2);

    companion object {
        fun fromInt(value: Int): Priority = when (value) {
            0 -> LOW
            1 -> MEDIUM
            2 -> HIGH
            else -> throw IllegalArgumentException("Invalid priority value: $value")
        }
    }
}