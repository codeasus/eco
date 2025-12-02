package codeasus.projects.bank.eco.feature.card.presentation

import androidx.lifecycle.viewModelScope
import codeasus.projects.bank.eco.core.ui.shared.mappers.toBankAccountUi
import codeasus.projects.bank.eco.core.ui.shared.viewmodel.base.BaseViewModel
import codeasus.projects.bank.eco.domain.local.repository.user.BankAccountRepository
import codeasus.projects.bank.eco.domain.local.repository.user.UserRepository
import codeasus.projects.bank.eco.core.ui.shared.view.states.BankAccountUiState
import codeasus.projects.bank.eco.feature.card.presentation.states.CardIntent
import codeasus.projects.bank.eco.feature.card.presentation.states.CardState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(
    userRepository: UserRepository,
    private val bankAccountRepository: BankAccountRepository
) :
    BaseViewModel(userRepository) {

    private val _state = MutableStateFlow(CardState())
    val state = _state.asStateFlow()

    fun handleIntent(intent: CardIntent) {
        when (intent) {
            is CardIntent.LoadCard -> loadCard(intent.bankAccountId)
            is CardIntent.FlipCard -> flipCard()
            is CardIntent.ShowBottomSheet -> {
                showBottomSheet()
                loadCardPrivateData()
            }

            is CardIntent.HideBottomSheet -> {
                hideBottomSheet()
                nullifyCardPrivateData()
            }

            is CardIntent.FreezeCard -> freezeCard()
        }
    }

    private fun loadCard(bankAccountId: Long) {
        viewModelScope.launch {
            _state.emit(_state.value.copy(bankAccountId = bankAccountId))
            _state.emit(_state.value.copy(bankAccountUiState = BankAccountUiState.Loading))
            delay(1000)
            val bankAccount = bankAccountRepository.getBankAccountForPublicById(bankAccountId)

            if (bankAccount == null) {
                _state.emit(_state.value.copy(bankAccountUiState = BankAccountUiState.NotFound))
                return@launch
            }
            _state.emit(_state.value.copy(bankAccountUiState = BankAccountUiState.Success(bankAccount.toBankAccountUi())))
        }
    }

    private fun loadCardPrivateData() {
        viewModelScope.launch {
            val bankAccountId = _state.value.bankAccountId
            if (bankAccountId > 0) {
                _state.emit(_state.value.copy(bankAccountPrivateDataUiState = BankAccountUiState.Loading))
                delay(1200)
                val bankAccount = bankAccountRepository.getBankAccountForPrivateById(bankAccountId)

                if (bankAccount == null) {
                    _state.emit(_state.value.copy(bankAccountPrivateDataUiState = BankAccountUiState.NotFound))
                    return@launch
                }
                _state.emit(_state.value.copy(bankAccountPrivateDataUiState = BankAccountUiState.Success(bankAccount.toBankAccountUi())))
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

    private fun freezeCard() {
        // Implement freeze card logic here
    }

    private fun showBottomSheet() {
        _state.value = _state.value.copy(showBottomSheet = true)
    }

    private fun hideBottomSheet() {
        _state.value = _state.value.copy(showBottomSheet = false)
    }
}