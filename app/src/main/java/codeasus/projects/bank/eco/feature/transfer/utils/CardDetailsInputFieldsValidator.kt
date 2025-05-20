package codeasus.projects.bank.eco.feature.transfer.utils

import codeasus.projects.bank.eco.core.ui.shared.view.utils.InputValidationResult

class CardDetailsInputFieldsValidator {
    companion object {
        fun validateCardNumber(cardNumber: String): InputValidationResult<String> {
            if (cardNumber.isBlank()) {
                return InputValidationResult.Invalid("Card number cannot be empty")
            }
            val digitsCount = cardNumber.count { it.isDigit() }
            return if (digitsCount != 16) {
                InputValidationResult.Invalid("Invalid card number")
            } else {
                InputValidationResult.Valid(cardNumber)
            }
        }

        fun validateRecipientName(cardHolderName: String): InputValidationResult<String> {
            if (cardHolderName.isBlank()) {
                return InputValidationResult.Invalid("Full name cannot be empty")
            }
            return InputValidationResult.Valid(cardHolderName)
        }

        fun validateTransferAmount(strAmount: String): InputValidationResult<Double> {
            if (strAmount.isBlank()) {
                return InputValidationResult.Invalid("Amount cannot be empty")
            }
            try {
                val amount = strAmount.toDouble()
                if (amount < 5.0) {
                    return InputValidationResult.Invalid("Amount must be at least 5.0")
                }
                return InputValidationResult.Valid(amount)
            } catch (numberFormatException: NumberFormatException) {
                return InputValidationResult.Invalid("Invalid amount")
            }
        }
    }
}