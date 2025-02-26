package codeasus.projects.bank.eco.feature.transfer.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import codeasus.projects.bank.eco.core.ui.shared.viewmodel.base.BaseViewModel
import codeasus.projects.bank.eco.domain.local.model.customer.CustomerModel
import codeasus.projects.bank.eco.domain.local.repository.customer.CustomerRepository
import codeasus.projects.bank.eco.domain.local.repository.transaction.TransactionRepository
import codeasus.projects.bank.eco.domain.local.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor(
    userRepository: UserRepository,
    customerRepository: CustomerRepository,
    transactionRepository: TransactionRepository
) :
    BaseViewModel(userRepository, customerRepository, transactionRepository) {
    var isTermsAndConditionsAccepted by mutableStateOf(false)
        private set

    var isStandardTransferEnabled by mutableStateOf(false)
        private set

    var selectedCustomer by mutableStateOf<CustomerModel?>(null)
        private set

    fun enableStandardTransfer() {
        isStandardTransferEnabled = !isStandardTransferEnabled
    }

    fun acceptPrivacyPolicy() {
        isTermsAndConditionsAccepted = !isTermsAndConditionsAccepted
    }

    fun selectCustomer(customer: CustomerModel) {
        selectedCustomer = customer
    }
}