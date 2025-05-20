package codeasus.projects.bank.eco.core.ui.shared.view.utils

sealed class InputValidationResult<out T> {
    data object Empty : InputValidationResult<Nothing>()
    data class Valid<T>(val data: T) : InputValidationResult<T>()
    data class Invalid(val errorMessage: String) : InputValidationResult<Nothing>()
}