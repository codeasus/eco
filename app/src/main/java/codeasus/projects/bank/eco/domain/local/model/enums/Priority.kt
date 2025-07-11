package codeasus.projects.bank.eco.domain.local.model.enums

enum class Priority(val value: Int, val label: String) {
    LOW(0, "Low"),
    MEDIUM(1, "Medium"),
    HIGH(2, "High");

    companion object {
        fun fromInt(value: Int): Priority = when (value) {
            0 -> LOW
            1 -> MEDIUM
            2 -> HIGH
            else -> throw IllegalArgumentException("Invalid priority value: $value")
        }
    }
}