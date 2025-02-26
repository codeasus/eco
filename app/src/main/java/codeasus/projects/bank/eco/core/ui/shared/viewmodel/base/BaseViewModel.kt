package codeasus.projects.bank.eco.core.ui.shared.viewmodel.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import codeasus.projects.bank.eco.domain.local.model.customer.CustomerModel
import codeasus.projects.bank.eco.domain.local.model.enums.Currency
import codeasus.projects.bank.eco.domain.local.repository.customer.CustomerRepository
import codeasus.projects.bank.eco.domain.local.repository.transaction.TransactionRepository
import codeasus.projects.bank.eco.domain.local.repository.user.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

abstract class BaseViewModel(
    private val userRepository: UserRepository,
    private val customerRepository: CustomerRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private val _userState = MutableStateFlow<codeasus.projects.bank.eco.domain.local.model.user.UserModel?>(null)
    val userState: StateFlow<codeasus.projects.bank.eco.domain.local.model.user.UserModel?> = _userState.asStateFlow()

    private val _customersState = MutableStateFlow<List<CustomerModel>>(emptyList())
    val customerStates: StateFlow<List<CustomerModel>> = _customersState

    private val _customerTransactionPairsState =
        MutableStateFlow<List<Pair<CustomerModel, codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel>>>(emptyList())

    val customerTransactionPairsState: StateFlow<List<Pair<CustomerModel, codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel>>> =
        _customerTransactionPairsState

    init {
        loadUser()
        loadCustomers()
        loadCustomerTransactionPairs()
    }

    private fun loadUser() {
        viewModelScope.launch {
            try {
                val user = userRepository.loadUser()
                _userState.value = user
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun saveUser(user: codeasus.projects.bank.eco.domain.local.model.user.UserModel) {
        viewModelScope.launch {
            try {
                userRepository.saveUser(user)
                _userState.value = user
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun loadCustomers() {
        viewModelScope.launch {
            customerRepository.getAllCustomers().collectLatest { customersList ->
                _customersState.value = customersList
            }
        }
    }

    private fun loadCustomerTransactionPairs() {
        Log.d("CustomerTransactionPairs", "Called loadCustomerTransactionPairs")
        viewModelScope.launch {
            customerRepository.getAllCustomers().combine(transactionRepository.getAllTransactions()) { customers, transactions ->
                    val pairs = mutableListOf<Pair<CustomerModel, codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel>>()
                    transactions.forEach { transaction ->
                        customers.forEach { customer ->
                            if (transaction.toAccountNumber == customer.bankAccount.number) {
                                Log.d("CustomerTransactionPairs", "Processing transaction: $transaction")
                                pairs.add(Pair(customer, transaction))
                            }
                        }
                    }
                    pairs
                }.collectLatest { pairs ->
                _customerTransactionPairsState.value = pairs
            }
        }
    }

    fun saveTransaction(
        fromAccountNumber: String,
        toAccountNumber: String,
        amount: Double,
        currency: Currency = Currency.USD,
        rate: Double
    ) {
        viewModelScope.launch {
            val transaction =
                codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel(
                    fromAccountNumber = fromAccountNumber,
                    toAccountNumber = toAccountNumber,
                    amount = amount,
                    currency = currency,
                    rate = rate
                )
            transactionRepository.saveTransaction(transaction)
        }
    }

}