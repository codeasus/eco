package codeasus.projects.bank.eco.feature.card.presentation

import androidx.lifecycle.viewModelScope
import codeasus.projects.bank.eco.core.ui.shared.viewmodel.base.BaseViewModel
import codeasus.projects.bank.eco.domain.local.repository.user.UserRepository
import codeasus.projects.bank.eco.feature.card.presentation.states.CardIntent
import codeasus.projects.bank.eco.feature.card.presentation.states.CardState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(userRepository: UserRepository) :
    BaseViewModel(userRepository) {

    private val _state = MutableStateFlow(CardState())
    val state = _state.asStateFlow()

    fun handleIntent(intent: CardIntent) {
        when (intent) {
            is CardIntent.LoadCard -> loadCard(intent.bankAccountId)
            is CardIntent.FlipCard -> flipCard()
            is CardIntent.ShowBottomSheet -> showBottomSheet()
            is CardIntent.HideBottomSheet -> hideBottomSheet()
            is CardIntent.HandleCardManagementAction -> handleCardManagementAction(intent.actionId)
            is CardIntent.FreezeCard -> freezeCard()
        }
    }

    private fun loadCard(bankAccountId: String) {
        viewModelScope.launch {
            user.collectLatest { user ->
                val bankAccount =
                    user?.bankAccounts?.find { accounts -> accounts.id == bankAccountId }
                _state.value = _state.value.copy(bankAccount = bankAccount)
            }
        }
    }

    private fun handleCardManagementAction(actionId: Int) {
        when (actionId) {
            0 -> {
            }

            1 -> {
            }

            2 -> {
            }

            3 -> {
            }

            4 -> {
            }

            5 -> {
            }
        }
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