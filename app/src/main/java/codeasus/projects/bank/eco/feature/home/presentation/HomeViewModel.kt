package codeasus.projects.bank.eco.feature.home.presentation

import androidx.lifecycle.viewModelScope
import codeasus.projects.bank.eco.R
import codeasus.projects.bank.eco.core.ui.shared.mappers.toBankAccountUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.BankAccountUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.CustomerUi
import codeasus.projects.bank.eco.core.ui.shared.view.utils.InputField
import codeasus.projects.bank.eco.core.ui.shared.view.utils.InputValidationResult
import codeasus.projects.bank.eco.core.ui.shared.viewmodel.base.BaseViewModel
import codeasus.projects.bank.eco.domain.local.repository.user.BankAccountRepository
import codeasus.projects.bank.eco.domain.local.repository.user.UserRepository
import codeasus.projects.bank.eco.domain.local.usecase.GetAllTransactionsListItemsUseCase
import codeasus.projects.bank.eco.domain.local.usecase.GetFriendsUseCase
import codeasus.projects.bank.eco.domain.local.usecase.GetTransactionByIdUseCase
import codeasus.projects.bank.eco.feature.home.presentation.states.HomeIntent
import codeasus.projects.bank.eco.feature.home.presentation.states.HomeState
import codeasus.projects.bank.eco.feature.home.presentation.states.toTransactionDateItemUI
import codeasus.projects.bank.eco.feature.request_money.state.RequestMoneyState
import codeasus.projects.bank.eco.feature.transfer.utils.CardDetailsInputFieldsValidator
import codeasus.projects.bank.eco.feature.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    userRepository: UserRepository,
    private val getFriendsUseCase: GetFriendsUseCase,
    private val bankAccountRepository: BankAccountRepository,
    private val getTransactionByIdUseCase: GetTransactionByIdUseCase,
    private val getAllTransactionsListItemsUseCase: GetAllTransactionsListItemsUseCase
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
            is HomeIntent.ShowTransactionViewBottomSheet -> {
                showTransactionViewBottomSheet()
                loadTransactionById(intent.transactionId)
            }
            is HomeIntent.HideTransactionBottomBottomSheet -> {
                hideTransactionBottomBottomSheet()
            }
            is HomeIntent.ShowRequestMoneyBottomBottomSheet -> {
                showRequestMoneyBottomBottomSheet()
                loadFriends()
                setAccounts()
                setBeneficiaryBankAccount(_state.value.currentBankAccount)
            }
            is HomeIntent.HideRequestMoneyBottomBottomSheet -> {
                hideRequestMoneyBottomBottomSheet()
            }
            is HomeIntent.SelectFriend -> {
                setFriend(intent.friend)
            }
            is HomeIntent.SetTransferAmount -> {
                setTransferAmount(intent.strAmount)
            }
            is HomeIntent.SetBeneficiaryBankAccount -> {
                setBeneficiaryBankAccount(intent.bankAccount)
            }
        }
    }

    private fun loadTransactionById(id: String) {
        viewModelScope.launch {
            val transaction = getTransactionByIdUseCase.invoke(id)
            if (transaction != null) {
                _state.update { it.copy(transactionUiState = UiState.Success(transaction)) }
            }
        }
    }

    private fun loadAllTransactions() {
        viewModelScope.launch {
            val transactions = getAllTransactionsListItemsUseCase.invoke().map { it.toTransactionDateItemUI() }
            _state.update { it.copy(transactions = transactions) }
        }
    }

    private fun loadUser() {
        viewModelScope.launch {
            user.collect { user ->
                if (user != null) {
                    _state.update { it.copy(user = user) }
                }
            }
        }
    }

    private fun loadCards() {
        viewModelScope.launch {
            _state.update { it.copy(bankAccountsUiState = UiState.Loading) }
            val bankAccounts = bankAccountRepository.getBankAccounts().map { it.toBankAccountUi() }
            delay(800L)
            _state.update {
                it.copy(
                    bankAccountsUiState = UiState.Success(bankAccounts),
                    currentBankAccount = bankAccounts.first()
                )
            }
        }
    }

    private fun reStackCards() {
        viewModelScope.launch {
            if (_state.value.bankAccountsUiState is UiState.Success) {
                val bankAccounts = (_state.value.bankAccountsUiState as UiState.Success<List<BankAccountUi>>).data.toMutableList()

                if (bankAccounts.isEmpty()) return@launch

                val first = bankAccounts.first()
                var last = bankAccounts.last()
                for (i in bankAccounts.size - 1 downTo 1) {
                    val temp = bankAccounts[i - 1]
                    bankAccounts[i - 1] = last
                    last = temp
                }
                bankAccounts[bankAccounts.size - 1] = first

                _state.update {
                    it.copy(
                        bankAccountsUiState = UiState.Success(bankAccounts),
                        currentBankAccount = bankAccounts.first()
                    )
                }
            }
        }
    }

    fun setFriend(friend: CustomerUi) {
        _state.update { it.copy(requestMoneyState = it.requestMoneyState.copy(friend = friend)) }
    }

    fun setBeneficiaryBankAccount(bankAccountUi: BankAccountUi) {
        _state.update { it.copy(requestMoneyState = it.requestMoneyState.copy(beneficiaryBankAccount = UiState.Success(bankAccountUi))) }
    }

    fun setAccounts() {
        val accounts = _state.value.bankAccountsUiState
        _state.update { it.copy(requestMoneyState = it.requestMoneyState.copy(accounts = accounts)) }
    }

    fun setTransferAmount(strAmount: String) {
        val validationResponse = CardDetailsInputFieldsValidator.validateTransferAmount(strAmount)
        when (validationResponse) {
            is InputValidationResult.Valid -> {
                _state.update { it.copy(requestMoneyState = it.requestMoneyState.copy(amount = validationResponse.data)) }
            }
            else -> {}
        }
        updateInputFieldValidationStatus(InputField.TransferAmount, validationResponse)
    }

    fun loadFriends() {
        viewModelScope.launch {
            _state.update { it.copy(requestMoneyState = it.requestMoneyState.copy(friends = UiState.Loading)) }
            val friends = getFriendsUseCase.invoke()
            _state.update { it.copy(requestMoneyState = it.requestMoneyState.copy(friends = UiState.Success(friends))) }
        }
    }

    private fun updateInputFieldValidationStatus(inputField: InputField, validationResult: InputValidationResult<Any>) {
        val newMap = _state.value.requestMoneyState.inputFieldValidationStates.toMutableMap()

        newMap[inputField] = validationResult

        _state.update { it.copy(requestMoneyState = it.requestMoneyState.copy(inputFieldValidationStates = newMap)) }
    }

    private fun showTransactionViewBottomSheet() {
        _state.update { it.copy(showTransactionViewBottomSheet = true, transactionUiState = UiState.Loading) }
    }

    private fun hideTransactionBottomBottomSheet() {
        _state.update { it.copy(showTransactionViewBottomSheet = false, transactionUiState = UiState.Empty) }
    }

    private fun showRequestMoneyBottomBottomSheet() {
        _state.update { it.copy(showRequestMoneyBottomSheet = true) }
    }

    private fun hideRequestMoneyBottomBottomSheet() {
        _state.update { it.copy(showRequestMoneyBottomSheet = false, requestMoneyState = RequestMoneyState()) }
    }
}