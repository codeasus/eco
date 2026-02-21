package codeasus.projects.bank.eco.feature.transfer.states

import codeasus.projects.bank.eco.core.ui.shared.mappers.toUserUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.CustomerUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.UserUi
import codeasus.projects.bank.eco.core.ui.shared.view.utils.DataSourceDefaults
import codeasus.projects.bank.eco.core.ui.shared.view.utils.InputField
import codeasus.projects.bank.eco.core.ui.shared.view.utils.InputValidationResult
import codeasus.projects.bank.eco.domain.remote.model.banking.BinLookupModel
import codeasus.projects.bank.eco.feature.utils.UiState

data class TransferState(
    val user: UserUi = DataSourceDefaults.unknownUser.first.toUserUi(),
    val friends: UiState<List<CustomerUi>> = UiState.Empty,
    val transaction: TransactionState = TransactionState(),
    val binLookupResultState: UiState<BinLookupModel> = UiState.Empty,
    val inputFieldValidationStates: MutableMap<InputField, InputValidationResult<Any>> = mutableMapOf(
        InputField.CardNumber to InputValidationResult.Empty,
        InputField.RecipientName to InputValidationResult.Empty,
        InputField.TransferAmount to InputValidationResult.Empty
    )
)