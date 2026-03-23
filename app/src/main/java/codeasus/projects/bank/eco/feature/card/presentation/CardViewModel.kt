package codeasus.projects.bank.eco.feature.card.presentation

import androidx.lifecycle.viewModelScope
import codeasus.projects.bank.eco.core.ui.shared.mappers.toBankAccountUi
import codeasus.projects.bank.eco.core.ui.shared.viewmodel.base.BaseViewModel
import codeasus.projects.bank.eco.domain.local.repository.user.BankAccountRepository
import codeasus.projects.bank.eco.domain.local.repository.user.UserRepository
import codeasus.projects.bank.eco.domain.local.usecase.GetAllTransactionsListItemsUseCase
import codeasus.projects.bank.eco.domain.local.usecase.GetTransactionByIdUseCase
import codeasus.projects.bank.eco.feature.card.presentation.states.CardIntent
import codeasus.projects.bank.eco.feature.card.presentation.states.CardState
import codeasus.projects.bank.eco.feature.home.presentation.states.toTransactionDateItemUI
import codeasus.projects.bank.eco.feature.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(
    userRepository: UserRepository,
    private val bankAccountRepository: BankAccountRepository,
    private val getTransactionByIdUseCase: GetTransactionByIdUseCase,
    private val getAllTransactionsListItemsUseCase: GetAllTransactionsListItemsUseCase
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
            val transaction = getTransactionByIdUseCase.invoke(id)
            if(transaction != null) {
                _state.update { it.copy(transactionUiState = UiState.Success(transaction)) }
            }
        }
    }

    private fun loadAllTransactions() {
        viewModelScope.launch {
            _state.collectLatest { state ->
                when(val bankAccountUiState = state.bankAccountUiState) {
                    is UiState.Success -> {
                        val bankAccount = bankAccountUiState.data
                        val transactions = getAllTransactionsListItemsUseCase.invoke(bankAccount.id).map { it.toTransactionDateItemUI() }
                        _state.update { it.copy(transactions = transactions) }
                    }
                    else -> {}
                }
            }
        }
    }

    private fun loadCard(bankAccountId: String) {
        val currentState = _state.value
        if (currentState.bankAccountId == bankAccountId &&
            currentState.bankAccountUiState is UiState.Success
        ) {
            return
        }
        viewModelScope.launch {
            _state.update { it.copy(bankAccountId = bankAccountId) }
            _state.update { it.copy(bankAccountUiState = UiState.Loading) }
            delay(1000)
            val bankAccount = bankAccountRepository.getBankAccountForPublicById(bankAccountId)

            if (bankAccount == null) {
                _state.update { it.copy(bankAccountUiState = UiState.Empty) }
                return@launch
            }
            _state.update { it.copy(bankAccountUiState = UiState.Success(bankAccount.toBankAccountUi())) }
        }
    }

    private fun loadCardPrivateData() {
        viewModelScope.launch {
            val bankAccountId = _state.value.bankAccountId
            if (bankAccountId != null) {
                _state.update { it.copy(bankAccountPrivateDataUiState = UiState.Loading) }
                delay(1200)
                val bankAccount = bankAccountRepository.getBankAccountForPrivateById(bankAccountId)

                if (bankAccount == null) {
                    _state.update { it.copy(bankAccountPrivateDataUiState = UiState.Empty) }
                    return@launch
                }
                _state.update {
                    it.copy(
                        bankAccountPrivateDataUiState = UiState.Success(
                            bankAccount.toBankAccountUi()
                        )
                    )
                }
            }
        }
    }

    private fun nullifyCardPrivateData() {
        _state.update { it.copy(bankAccountPrivateDataUiState = UiState.Empty) }
    }

    private fun flipCard() {
        _state.update { it.copy(cardFlipState = _state.value.cardFlipState.next) }
    }

    private fun topUpCard() {
        // Implement topping-up the card logic here
    }

    private fun showCardDetailsBottomSheet() {
        _state.update { it.copy(showCardDetailsBottomSheet = true) }
    }

    private fun hideCardDetailsBottomSheet() {
        _state.update { it.copy(showCardDetailsBottomSheet = false) }
    }

    private fun showTransactionBottomSheet() {
        _state.update { it.copy(showTransactionBottomSheet = true, transactionUiState = UiState.Loading) }
    }

    private fun hideTransactionBottomSheet() {
        _state.update { it.copy(showTransactionBottomSheet = false, transactionUiState = UiState.Empty) }
    }
}