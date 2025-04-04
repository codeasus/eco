package codeasus.projects.bank.eco.feature.home.presentation

import androidx.lifecycle.viewModelScope
import codeasus.projects.bank.eco.core.ui.shared.viewmodel.base.BaseViewModel
import codeasus.projects.bank.eco.domain.local.model.customer.CustomerModel
import codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel
import codeasus.projects.bank.eco.domain.local.model.user.UserBankAccountModel
import codeasus.projects.bank.eco.domain.local.repository.transaction.TransactionRepository
import codeasus.projects.bank.eco.domain.local.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    userRepository: UserRepository,
    private val transactionRepository: TransactionRepository
) : BaseViewModel(userRepository) {

    private val _transactions = MutableStateFlow<List<Pair<CustomerModel, TransactionModel>>>(emptyList())
    val transactions: StateFlow<List<Pair<CustomerModel, TransactionModel>>> = _transactions

    private val _bankCards = MutableStateFlow<List<UserBankAccountModel>>(emptyList())
    val bankCards: StateFlow<List<UserBankAccountModel>> = _bankCards

    init {
        viewModelScope.launch {
            user.collectLatest {
                _bankCards.emit(it?.bankAccounts ?: emptyList())
            }
        }
        loadAllTransactions()
    }

    private fun loadAllTransactions() {
        viewModelScope.launch {
            val transactions = transactionRepository.getAllTransactions(null).map { Pair(it.value, it.key) }
            _transactions.emit(transactions)
        }
    }

    fun reStackCards() {
        viewModelScope.launch {
            val bankCards = _bankCards.value.toMutableList()

            if (bankCards.isEmpty()) return@launch

            val first = bankCards.first()
            var last = bankCards.last()
            for (i in bankCards.size - 1 downTo 1) {
                val temp = bankCards[i - 1]
                bankCards[i - 1] = last
                last = temp
            }
            bankCards[bankCards.size - 1] = first
            _bankCards.emit(bankCards)
        }
    }
}