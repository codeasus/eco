package codeasus.projects.bank.eco.feature.transfer.presentation

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.viewModelScope
import codeasus.projects.bank.eco.core.ui.shared.view.utils.InputValidationResult
import codeasus.projects.bank.eco.core.ui.shared.viewmodel.base.BaseViewModel
import codeasus.projects.bank.eco.domain.local.model.customer.CustomerModel
import codeasus.projects.bank.eco.domain.local.repository.customer.CustomerRepository
import codeasus.projects.bank.eco.domain.local.repository.user.UserRepository
import codeasus.projects.bank.eco.feature.transfer.states.TransactionState
import codeasus.projects.bank.eco.feature.transfer.utils.CardDetailsInputFieldsValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor(
    userRepository: UserRepository,
    private val customerRepository: CustomerRepository
) : BaseViewModel(userRepository) {
    private var _customers = MutableStateFlow<List<CustomerModel>>(emptyList())
    val customers = _customers.asStateFlow()

    private var _transactionState = MutableStateFlow(TransactionState())
    val transactionState = _transactionState.asStateFlow()

    val inputFieldValidationStates = mutableStateMapOf<String, InputValidationResult<Any>>(
        "cardNumber" to InputValidationResult.Empty,
        "recipientName" to InputValidationResult.Empty,
        "transferAmount" to InputValidationResult.Empty
    )

    init {
        getCustomers()
    }

    fun selectCurrency(currencyName: String) {
        viewModelScope.launch {
            _transactionState.emit(_transactionState.value.copy(currency = currencyName))
        }
    }

    fun setAmount(strAmount: String) {
        viewModelScope.launch {
            val validationResponse = CardDetailsInputFieldsValidator.validateTransferAmount(strAmount)
            when (validationResponse) {
                is InputValidationResult.Valid -> {
                    _transactionState.emit(_transactionState.value.copy(amount = validationResponse.data))
                    inputFieldValidationStates["transferAmount"] = validationResponse
                }
                else -> {}
            }
            inputFieldValidationStates["transferAmount"] = validationResponse
        }
    }

    fun setAccountNumber(accountNumber: String) {
        viewModelScope.launch {
            _transactionState.emit(_transactionState.value.copy(accountNumber = accountNumber))
            inputFieldValidationStates["cardNumber"] = CardDetailsInputFieldsValidator.validateCardNumber(accountNumber)
        }
    }

    fun setAccountName(accountName: String) {
        viewModelScope.launch {
            _transactionState.emit(_transactionState.value.copy(accountName = accountName))
            inputFieldValidationStates["recipientName"] = CardDetailsInputFieldsValidator.validateRecipientName(accountName)
        }
    }

    fun selectCustomer(customer: CustomerModel) {
        viewModelScope.launch {
            _transactionState.emit(
                _transactionState.value.copy(
                    accountName = customer.name,
                    accountNumber = customer.bankAccount.number
                )
            )
        }
    }

    private fun getCustomers() {
        viewModelScope.launch {
            _customers.emit(customerRepository.getAllCustomers())
        }
    }
}