package codeasus.projects.bank.eco.feature.transfer.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import codeasus.projects.bank.eco.core.ui.shared.viewmodel.base.BaseViewModel
import codeasus.projects.bank.eco.domain.local.model.customer.CustomerModel
import codeasus.projects.bank.eco.domain.local.model.enums.Currency
import codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel
import codeasus.projects.bank.eco.domain.local.repository.customer.CustomerRepository
import codeasus.projects.bank.eco.domain.local.repository.transaction.TransactionRepository
import codeasus.projects.bank.eco.domain.local.repository.user.UserRepository
import codeasus.projects.bank.eco.domain.remote.model.payment.PaymentModel
import codeasus.projects.bank.eco.domain.remote.model.response.ApiResponse
import codeasus.projects.bank.eco.domain.remote.repository.PaymentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor(
    userRepository: UserRepository,
    customerRepository: CustomerRepository,
    val transactionRepository: TransactionRepository,
    private val paymentRepository: PaymentRepository
) :
    BaseViewModel(userRepository, customerRepository, transactionRepository) {

    // Here could also be a PaymentModel as a state for much more rigid access but due to the mocking nature of the app, it was skipped and will be added asap

    var isTermsAndConditionsAccepted by mutableStateOf(false)
        private set

    var isStandardTransferEnabled by mutableStateOf(false)
        private set

    var selectedCustomer by mutableStateOf<CustomerModel?>(null)
        private set

    var amount by mutableDoubleStateOf(0.0)
        private set

    var amountText by mutableStateOf("")
        private set

    var isLoading by mutableStateOf(false)
        private set

    private val _paymentResult = MutableStateFlow<ApiResponse<String>?>(null)
    val paymentResult = _paymentResult.asStateFlow()

    fun updateAmount(input: String) {
        amountText = input
        amount = input.toDoubleOrNull() ?: 0.0
    }

    fun enableStandardTransfer() {
        isStandardTransferEnabled = !isStandardTransferEnabled
    }

    fun acceptPrivacyPolicy() {
        isTermsAndConditionsAccepted = !isTermsAndConditionsAccepted
    }

    fun selectCustomer(customer: CustomerModel) {
        selectedCustomer = customer
    }

    fun sendPayment() {
        viewModelScope.launch {
            if (amount <= 0) {
                _paymentResult.value = ApiResponse.Error(400, "Please enter a valid amount")
                return@launch
            }

            if (selectedCustomer == null) {
                _paymentResult.value = ApiResponse.Error(400, "Please select a recipient")
                return@launch
            }

            if (!isTermsAndConditionsAccepted) {
                _paymentResult.value = ApiResponse.Error(400, "Please accept terms and conditions")
                return@launch
            }

            val payment = PaymentModel(
                fromBankAccountNumber = selectedCustomer?.bankAccount?.number?: "",
                toBankAccountNumber = userState.value?.bankAccounts?.get(0)?.number?: "",
                amount = amount,
                currency = Currency.USD,
                isStandard = isStandardTransferEnabled
            )

            try {
                val result = paymentRepository.sendPayment(payment)
                _paymentResult.value = result

                if (result is ApiResponse.Success) {
//                    recordTransaction()
                }
            } catch (e: Exception) {
                _paymentResult.value = ApiResponse.Error(
                    500,
                    "An unexpected error occurred: ${e.message}"
                )
            } finally {
                isLoading = false
            }
        }
    }

    private suspend fun recordTransaction(transaction: TransactionModel) {
        // transactionRepository.saveTransaction(...)
        // Convert PaymentModel to TransactionModel
        transactionRepository.saveTransaction(transaction)
    }
}