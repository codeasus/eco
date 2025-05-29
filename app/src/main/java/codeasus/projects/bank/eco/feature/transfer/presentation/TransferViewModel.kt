package codeasus.projects.bank.eco.feature.transfer.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.viewModelScope
import codeasus.projects.bank.eco.core.ui.shared.view.utils.InputValidationResult
import codeasus.projects.bank.eco.core.ui.shared.viewmodel.base.BaseViewModel
import codeasus.projects.bank.eco.domain.local.model.customer.CustomerModel
import codeasus.projects.bank.eco.domain.local.model.enums.Currency
import codeasus.projects.bank.eco.domain.local.repository.customer.CustomerRepository
import codeasus.projects.bank.eco.domain.local.repository.user.UserRepository
import codeasus.projects.bank.eco.domain.remote.model.banking.BinLookupModel
import codeasus.projects.bank.eco.domain.remote.usecase.GetBinLookupUseCase
import codeasus.projects.bank.eco.domain.utils.DomainNetworkError
import codeasus.projects.bank.eco.domain.utils.DomainResult
import codeasus.projects.bank.eco.feature.transfer.states.TransactionState
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
    private var _customers = MutableStateFlow<List<CustomerModel>>(emptyList())
    val customers = _customers.asStateFlow()

    private var _transactionState = MutableStateFlow(TransactionState())
    val transactionState = _transactionState.asStateFlow()

    private val _binLookUpState = MutableStateFlow<UiState<BinLookupModel>>(UiState.Loading)
    val binLookupState =  _binLookUpState.asStateFlow()

    val inputFieldValidationStates = mutableStateMapOf<String, InputValidationResult<Any>>(
        "cardNumber" to InputValidationResult.Empty,
        "recipientName" to InputValidationResult.Empty,
        "transferAmount" to InputValidationResult.Empty
    )

    init {
        getCustomers()
    }

    private fun lookupBin(bin: String) {
        viewModelScope.launch {
            when (val result = getBinLookupUseCase(bin)) {
                is DomainResult.Success -> {
                    _binLookUpState.value = UiState.Success(result.data)
                }
                is DomainResult.Error -> {
                    val error = result.error as DomainNetworkError
                    _binLookUpState.emit(UiState.Error(error.message))
                }
            }
        }
    }

    fun selectCurrency(currency: Currency) {
        viewModelScope.launch {
            _transactionState.emit(_transactionState.value.copy(currency = currency))
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
            if(accountNumber.length == 8) {
                val incomingStr = accountNumber.take(8)
                val currentStr = _transactionState.value.accountNumber.take(8)
                if(currentStr != incomingStr) {
                    lookupBin(accountNumber.take(8))
                }
            }
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