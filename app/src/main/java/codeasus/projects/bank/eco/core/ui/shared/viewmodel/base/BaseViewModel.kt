package codeasus.projects.bank.eco.core.ui.shared.viewmodel.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import codeasus.projects.bank.eco.domain.local.model.customer.CustomerModel
import codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel
import codeasus.projects.bank.eco.domain.local.model.user.UserModel
import codeasus.projects.bank.eco.domain.local.repository.transaction.TransactionRepository
import codeasus.projects.bank.eco.domain.local.repository.user.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel(
    private val userRepository: UserRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private val _user = MutableStateFlow<UserModel?>(null)
    val user: StateFlow<UserModel?> = _user.asStateFlow()

    private val _transactions = MutableStateFlow<List<Pair<CustomerModel, TransactionModel>>>(emptyList())
    val transactions: StateFlow<List<Pair<CustomerModel, TransactionModel>>> = _transactions

    init {
        loadUser()
        loadAllTransactionBySelectedPhoneNumber()
    }

    private fun loadAllTransactionBySelectedPhoneNumber() {
        viewModelScope.launch {
            val transactions = transactionRepository.getAllTransactions(cardNumber = _user.value?.bankAccounts?.get(0)?.number).map { Pair(it.value, it.key) }
            _transactions.emit(transactions)
        }
    }

    private fun loadUser() {
        viewModelScope.launch {
            try {
                _user.emit(userRepository.loadUser())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}