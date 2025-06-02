package codeasus.projects.bank.eco.feature.card.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import codeasus.projects.bank.eco.core.ui.shared.viewmodel.base.BaseViewModel
import codeasus.projects.bank.eco.domain.local.model.user.UserBankAccountModel
import codeasus.projects.bank.eco.domain.local.repository.user.UserRepository
import codeasus.projects.bank.eco.feature.card.presentation.states.CardFlipState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(userRepository: UserRepository) : BaseViewModel(userRepository) {

    private val _bankAccount = MutableStateFlow<UserBankAccountModel?>(null)
    val bankAccount = _bankAccount.asStateFlow()

    var cardFlipState by mutableStateOf(CardFlipState.FRONT)
        private set

    fun loadCard(bankAccountId: String) {
        viewModelScope.launch {
            user.collectLatest {
                val bankAccount = it?.bankAccounts?.find { it.id == bankAccountId }
                _bankAccount.emit(bankAccount)
            }
        }
    }

    fun flip() {
        cardFlipState = cardFlipState.next
    }
}