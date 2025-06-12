package codeasus.projects.bank.eco.feature.home.presentation

import androidx.lifecycle.viewModelScope
import codeasus.projects.bank.eco.core.ui.shared.viewmodel.base.BaseViewModel
import codeasus.projects.bank.eco.domain.local.repository.transaction.TransactionRepository
import codeasus.projects.bank.eco.domain.local.repository.user.UserRepository
import codeasus.projects.bank.eco.feature.home.presentation.states.HomeIntent
import codeasus.projects.bank.eco.feature.home.presentation.states.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    userRepository: UserRepository,
    private val transactionRepository: TransactionRepository
) : BaseViewModel(userRepository) {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        loadCards()
        loadAllTransactions()
    }

    fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.ReStackCards -> reStackCards()
        }
    }

    private fun loadAllTransactions() {
        viewModelScope.launch {
            val transactions = transactionRepository.getAllTransactions(null).map { Pair(it.value, it.key) }
            _state.emit(_state.value.copy(transactions = transactions))
        }
    }

    private fun loadCards() {
        viewModelScope.launch {
            user.collectLatest {
                _state.emit(_state.value.copy(cards = it?.bankAccounts ?: emptyList()))
            }
        }
    }

    private fun reStackCards() {
        viewModelScope.launch {
            val bankCards = _state.value.cards.toMutableList()

            if (bankCards.isEmpty()) return@launch

            val first = bankCards.first()
            var last = bankCards.last()
            for (i in bankCards.size - 1 downTo 1) {
                val temp = bankCards[i - 1]
                bankCards[i - 1] = last
                last = temp
            }
            bankCards[bankCards.size - 1] = first
            _state.emit(_state.value.copy(cards = bankCards))
        }
    }
}