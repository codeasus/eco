package codeasus.projects.bank.eco.feature.card.presentation

import androidx.lifecycle.viewModelScope
import codeasus.projects.bank.eco.core.ui.shared.mappers.toBankAccountUi
import codeasus.projects.bank.eco.core.ui.shared.mappers.toCustomerUi
import codeasus.projects.bank.eco.core.ui.shared.mappers.toTransactionUi
import codeasus.projects.bank.eco.core.ui.shared.viewmodel.base.BaseViewModel
import codeasus.projects.bank.eco.domain.local.repository.user.BankAccountRepository
import codeasus.projects.bank.eco.domain.local.repository.user.UserRepository
import codeasus.projects.bank.eco.core.ui.shared.view.states.BankAccountUiState
import codeasus.projects.bank.eco.domain.local.repository.transaction.TransactionRepository
import codeasus.projects.bank.eco.feature.card.presentation.states.CardIntent
import codeasus.projects.bank.eco.feature.card.presentation.states.CardState
import codeasus.projects.bank.eco.feature.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(
    userRepository: UserRepository,
    private val bankAccountRepository: BankAccountRepository,
    private val transactionRepository: TransactionRepository
) :
    BaseViewModel(userRepository) {

    private val _state = MutableStateFlow(CardState())
    val state = _state.asStateFlow()

    fun handleIntent(intent: CardIntent) {
        when (intent) {
            is CardIntent.LoadCard -> {
                loadCard(intent.bankAccountId)
                loadAllTransactions()
            }
            is CardIntent.FlipCard -> flipCard()
            is CardIntent.ShowCardDetailsBottomSheet -> {
                showCardDetailsBottomSheet()
                loadCardPrivateData()
            }

            is CardIntent.HideCardDetailsBottomSheet -> {
                hideCardDetailsBottomSheet()
                nullifyCardPrivateData()
            }

            is CardIntent.ShowTransactionBottomSheet -> {
                showTransactionBottomSheet()
                loadTransactionById(intent.transactionId)
            }

            is CardIntent.HideTransactionBottomSheet -> {
                hideTransactionBottomSheet()
            }

            is CardIntent.TopUp -> topUpCard()
            is CardIntent.More -> {}
        }
    }

    private fun loadTransactionById(id: String) {
        viewModelScope.launch {
            val transaction = transactionRepository.getTransactionById(id)?.toTransactionUi(true)
            delay(500L)
            if(transaction != null) {
                _state.emit(_state.value.copy(transactionUiState = UiState.Success(transaction)))
            }
        }
    }

    private fun loadAllTransactions() {
        viewModelScope.launch {
            _state.collectLatest { state ->
                when(val bankAccountUiState = state.bankAccountUiState) {
                    is BankAccountUiState.Success -> {
                        val bankAccount = bankAccountUiState.data
                        val transactions = transactionRepository.getAllTransactions(bankAccount.id).map {
                                Pair(it.value.toCustomerUi(), it.key.toTransactionUi())
                            }
                        _state.emit(_state.value.copy(transactions = transactions))
                    }
                    else -> {}
                }
            }
        }
    }

    private fun loadCard(bankAccountId: String) {
        val currentState = _state.value
        if (currentState.bankAccountId == bankAccountId &&
            currentState.bankAccountUiState is BankAccountUiState.Success
        ) {
            return
        }
        viewModelScope.launch {
            _state.emit(_state.value.copy(bankAccountId = bankAccountId))
            _state.emit(_state.value.copy(bankAccountUiState = BankAccountUiState.Loading))
            delay(1000)
            val bankAccount = bankAccountRepository.getBankAccountForPublicById(bankAccountId)

            if (bankAccount == null) {
                _state.emit(_state.value.copy(bankAccountUiState = BankAccountUiState.NotFound))
                return@launch
            }
            _state.emit(
                _state.value.copy(
                    bankAccountUiState = BankAccountUiState.Success(
                        bankAccount.toBankAccountUi()
                    )
                )
            )
        }
    }

    private fun loadCardPrivateData() {
        viewModelScope.launch {
            val bankAccountId = _state.value.bankAccountId
            if (bankAccountId != null) {
                _state.emit(_state.value.copy(bankAccountPrivateDataUiState = BankAccountUiState.Loading))
                delay(1200)
                val bankAccount = bankAccountRepository.getBankAccountForPrivateById(bankAccountId)

                if (bankAccount == null) {
                    _state.emit(_state.value.copy(bankAccountPrivateDataUiState = BankAccountUiState.NotFound))
                    return@launch
                }
                _state.emit(
                    _state.value.copy(
                        bankAccountPrivateDataUiState = BankAccountUiState.Success(
                            bankAccount.toBankAccountUi()
                        )
                    )
                )
            }
        }
    }

    private fun nullifyCardPrivateData() {
        _state.value = _state.value.copy(bankAccountPrivateDataUiState = BankAccountUiState.Idle)
    }

    private fun flipCard() {
        _state.value = _state.value.copy(
            cardFlipState = _state.value.cardFlipState.next
        )
    }

    private fun topUpCard() {
        // Implement topping-up the card logic here
    }

    private fun showCardDetailsBottomSheet() {
        _state.value = _state.value.copy(showCardDetailsBottomSheet = true)
    }

    private fun hideCardDetailsBottomSheet() {
        _state.value = _state.value.copy(showCardDetailsBottomSheet = false)
    }

    private fun showTransactionBottomSheet() {
        _state.value = _state.value.copy(showTransactionBottomSheet = true, transactionUiState = UiState.Loading)
    }

    private fun hideTransactionBottomSheet() {
        _state.value = _state.value.copy(showTransactionBottomSheet = false, transactionUiState = UiState.Empty)
    }
}