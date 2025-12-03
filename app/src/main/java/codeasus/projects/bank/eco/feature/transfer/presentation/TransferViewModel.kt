package codeasus.projects.bank.eco.feature.transfer.presentation

import androidx.lifecycle.viewModelScope
import codeasus.projects.bank.eco.core.ui.shared.mappers.toCustomerUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.CustomerUi
import codeasus.projects.bank.eco.core.ui.shared.view.utils.InputValidationResult
import codeasus.projects.bank.eco.core.ui.shared.viewmodel.base.BaseViewModel
import codeasus.projects.bank.eco.domain.local.model.enums.Currency
import codeasus.projects.bank.eco.domain.local.repository.customer.CustomerRepository
import codeasus.projects.bank.eco.domain.local.repository.user.UserRepository
import codeasus.projects.bank.eco.domain.remote.usecase.GetBinLookupUseCase
import codeasus.projects.bank.eco.domain.utils.DomainResult
import codeasus.projects.bank.eco.feature.transfer.states.InputField
import codeasus.projects.bank.eco.feature.transfer.states.TransferIntent
import codeasus.projects.bank.eco.feature.transfer.states.TransferState
import codeasus.projects.bank.eco.feature.transfer.utils.CardDetailsInputFieldsValidator
import codeasus.projects.bank.eco.feature.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor(
    userRepository: UserRepository,
    private val customerRepository: CustomerRepository,
    private val getBinLookupUseCase: GetBinLookupUseCase
) : BaseViewModel(userRepository) {

    private val _state = MutableStateFlow(TransferState())
    val state = _state.asStateFlow()

    init {
        loadCustomers()
    }

    fun handleIntent(intent: TransferIntent) {
        when (intent) {
            is TransferIntent.SelectCurrency -> selectCurrency(intent.currency)
            is TransferIntent.SetTransferAmount -> setTransferAmount(intent.amount)
            is TransferIntent.SetBeneficiaryName -> setBeneficiaryName(intent.beneficiaryName)
            is TransferIntent.SetAccountNumber -> setAccountNumber(intent.accountNumber)
            is TransferIntent.SelectCustomer -> selectCustomer(intent.customerUi)
        }
    }

    private fun lookupBin(bin: String) {
        viewModelScope.launch {
            when (val result = getBinLookupUseCase(bin)) {
                is DomainResult.Success -> {
                    _state.emit(_state.value.copy(binLookupResultState = UiState.Success(result.data)))
                }

                is DomainResult.Error -> {}
            }
        }
    }

    private fun selectCurrency(currency: Currency) {
        _state.value = _state.value.copy(transaction = _state.value.transaction.copy(currency = currency))
    }

    private fun setTransferAmount(strAmount: String) {
        viewModelScope.launch {
            val validationResponse = CardDetailsInputFieldsValidator.validateTransferAmount(strAmount)
            when (validationResponse) {
                is InputValidationResult.Valid -> {
                    _state.emit(_state.value.copy(transaction = _state.value.transaction.copy(amount = validationResponse.data)))
                    updateInputFieldValidationStatus(InputField.TransferAmount, validationResponse)
                }
                else -> {}
            }
            updateInputFieldValidationStatus(InputField.TransferAmount, validationResponse)
        }
    }

    private fun setAccountNumber(accountNumber: String) {
        if (accountNumber.length == 8) {
            val incomingStr = accountNumber.take(8)
            val currentStr = _state.value.transaction.accountNumber.take(8)
            if (currentStr != incomingStr) {
                lookupBin(accountNumber.take(8))
            }
        }
        _state.value = _state.value.copy(transaction = _state.value.transaction.copy(accountNumber = accountNumber))
        updateInputFieldValidationStatus(InputField.CardNumber, CardDetailsInputFieldsValidator.validateCardNumber(accountNumber))
    }

    private fun setBeneficiaryName(accountName: String) {
        _state.value = _state.value.copy(transaction = _state.value.transaction.copy(accountName = accountName))
        updateInputFieldValidationStatus(InputField.RecipientName, CardDetailsInputFieldsValidator.validateRecipientName(accountName))
    }

    private fun selectCustomer(customer: CustomerUi) {
        viewModelScope.launch {
            _state.emit(_state.value.copy(transaction = _state.value.transaction.copy(accountName = customer.name, accountNumber = customer.bankAccount.number)))
        }
    }

    private fun loadCustomers() {
        viewModelScope.launch {
            _state.emit(_state.value.copy(customers = customerRepository.getAllCustomers().map { it.toCustomerUi() }))
        }
    }

    private fun updateInputFieldValidationStatus(inputField: InputField, validationResult: InputValidationResult<Any>) {
        val copyInputFieldValidationStates = _state.value.inputFieldValidationStates.let {
            it[inputField] = validationResult
            it
        }
        _state.value = _state.value.copy(inputFieldValidationStates = copyInputFieldValidationStates)
    }
}