package codeasus.projects.bank.eco.feature.home.presentation

import androidx.lifecycle.viewModelScope
import codeasus.projects.bank.eco.core.ui.shared.mappers.toBankAccountUi
import codeasus.projects.bank.eco.core.ui.shared.mappers.toCustomerUi
import codeasus.projects.bank.eco.core.ui.shared.mappers.toTransactionUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.BankAccountUi
import codeasus.projects.bank.eco.core.ui.shared.view.states.BankAccountUiState
import codeasus.projects.bank.eco.core.ui.shared.viewmodel.base.BaseViewModel
import codeasus.projects.bank.eco.domain.local.repository.transaction.TransactionRepository
import codeasus.projects.bank.eco.domain.local.repository.user.BankAccountRepository
import codeasus.projects.bank.eco.domain.local.repository.user.UserRepository
import codeasus.projects.bank.eco.feature.home.presentation.states.HomeIntent
import codeasus.projects.bank.eco.feature.home.presentation.states.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    userRepository: UserRepository,
    private val transactionRepository: TransactionRepository,
    private val bankAccountRepository: BankAccountRepository
) : BaseViewModel(userRepository) {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        loadUser()
        loadCards()
        loadAllTransactions()
    }

    fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.RestackCards -> reStackCards()
        }
    }

    private fun loadAllTransactions() {
        viewModelScope.launch {
            val transactions = transactionRepository.getAllTransactions(null)
                .map { Pair(it.value.toCustomerUi(), it.key.toTransactionUi()) }
            _state.emit(_state.value.copy(transactions = transactions))
        }
    }

    private fun loadUser() {
        viewModelScope.launch {
            user.collect {  user ->
                if(user != null) {
                    _state.emit(_state.value.copy(user = user))
                }
            }
        }
    }

    private fun loadCards() {
        viewModelScope.launch {
            _state.emit(_state.value.copy(bankAccountsUiState = BankAccountUiState.Loading))
            val bankAccounts = bankAccountRepository.getBankAccounts().map { it.toBankAccountUi() }
            delay(800)
            _state.emit(_state.value.copy(bankAccountsUiState = BankAccountUiState.Success(bankAccounts), currentBankAccount = bankAccounts.first()))
        }
    }

    private fun reStackCards() {
        viewModelScope.launch {
            if (_state.value.bankAccountsUiState is BankAccountUiState.Success) {
                val bankAccounts = (_state.value.bankAccountsUiState as BankAccountUiState.Success<List<BankAccountUi>>).data.toMutableList()

                if (bankAccounts.isEmpty()) return@launch

                val first = bankAccounts.first()
                var last = bankAccounts.last()
                for (i in bankAccounts.size - 1 downTo 1) {
                    val temp = bankAccounts[i - 1]
                    bankAccounts[i - 1] = last
                    last = temp
                }
                bankAccounts[bankAccounts.size - 1] = first
                _state.emit(_state.value.copy(bankAccountsUiState = BankAccountUiState.Success(bankAccounts), currentBankAccount = bankAccounts.first()))
            }
        }
    }
}