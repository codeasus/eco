package codeasus.projects.bank.eco.feature.request_money.state

import codeasus.projects.bank.eco.core.ui.shared.view.models.BankAccountUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.CustomerUi
import codeasus.projects.bank.eco.core.ui.shared.view.utils.InputField
import codeasus.projects.bank.eco.core.ui.shared.view.utils.InputValidationResult
import codeasus.projects.bank.eco.feature.utils.UiState

data class RequestMoneyState(
    val beneficiaryBankAccount: UiState<BankAccountUi> = UiState.Empty,
    val accounts: UiState<List<BankAccountUi>> = UiState.Empty,
    val amount: Double = 0.0,
    val friend: CustomerUi? = null,
    val friends: UiState<List<CustomerUi>> = UiState.Empty,
    val inputFieldValidationStates: Map<InputField, InputValidationResult<Any>> = mutableMapOf(
        InputField.TransferAmount to InputValidationResult.Empty
    )
)