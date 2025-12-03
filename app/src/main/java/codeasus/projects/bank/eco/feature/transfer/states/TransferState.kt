package codeasus.projects.bank.eco.feature.transfer.states

import codeasus.projects.bank.eco.core.ui.shared.view.models.CustomerUi
import codeasus.projects.bank.eco.core.ui.shared.view.utils.InputValidationResult
import codeasus.projects.bank.eco.domain.remote.model.banking.BinLookupModel
import codeasus.projects.bank.eco.feature.utils.UiState

enum class InputField {
    CardNumber,
    RecipientName,
    TransferAmount
}

data class TransferState(
    val isLoading: Boolean = false,
    val customers: List<CustomerUi> = emptyList(),
    val transaction: TransactionState = TransactionState(),
    val binLookupResultState: UiState<BinLookupModel> = UiState.Empty,
    val inputFieldValidationStates: MutableMap<InputField, InputValidationResult<Any>> = mutableMapOf(
        InputField.CardNumber to InputValidationResult.Empty,
        InputField.RecipientName to InputValidationResult.Empty,
        InputField.TransferAmount to InputValidationResult.Empty
    ),
    val error: String? = null
)
