package codeasus.projects.bank.eco.feature.search_transaction.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import codeasus.projects.bank.eco.domain.local.model.enums.TransactionType
import codeasus.projects.bank.eco.domain.local.usecase.GetAllTransactionsListItemsUseCase
import codeasus.projects.bank.eco.domain.local.usecase.GetTransactionByIdUseCase
import codeasus.projects.bank.eco.domain.local.usecase.GetTransactionsByKeywordAndTypeUseCase
import codeasus.projects.bank.eco.domain.local.usecase.GetTransactionsByKeywordUseCase
import codeasus.projects.bank.eco.domain.local.usecase.GetTransactionsByTypeUseCase
import codeasus.projects.bank.eco.feature.home.presentation.states.toTransactionDateItemUI
import codeasus.projects.bank.eco.feature.search_transaction.states.SearchTransactionIntent
import codeasus.projects.bank.eco.feature.search_transaction.states.SearchTransactionState
import codeasus.projects.bank.eco.feature.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchTransactionViewModel @Inject constructor(
    private val getTransactionByIdUseCase: GetTransactionByIdUseCase,
    private val getTransactionsByTypeUseCase: GetTransactionsByTypeUseCase,
    private val getTransactionByKeywordUseCase: GetTransactionsByKeywordUseCase,
    private val getAllTransactionsListItemsUseCase: GetAllTransactionsListItemsUseCase,
    private val getAllTransactionsByKeywordAndTypeUseCase: GetTransactionsByKeywordAndTypeUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(SearchTransactionState())
    val state = _state.asStateFlow()

    init {
        observeSearchParameters()
    }

    fun handleIntent(intent: SearchTransactionIntent) {
        when (intent) {
            is SearchTransactionIntent.ConfigureBankAccountId -> {
                _state.update {
                    it.copy(searchParametersState = it.searchParametersState.copy(bankAccountId = intent.accountId))
                }
            }
            is SearchTransactionIntent.ToggleSearchTextVisibility -> toggleSearchTextVisibility()
            is SearchTransactionIntent.SetSearchText -> setSearchText(intent.text)
            is SearchTransactionIntent.SelectTransactionType -> setTransactionType(intent.type)
            is SearchTransactionIntent.ShowBottomSheet -> {
                showBottomSheet()
                getTransactionById(intent.transactionId)
            }

            is SearchTransactionIntent.HideBottomSheet -> {
                hideBottomSheet()
            }
        }
    }

    fun observeSearchParameters () {
        viewModelScope.launch {
            _state
                .map { it.searchParametersState }
                .distinctUntilChanged()
                .debounce { 500L }
                .collectLatest { searchParametersState ->
                    fetchTransactions(searchParametersState.bankAccountId, searchParametersState.searchText, searchParametersState.selectedTransactionTypes)
                }
        }
    }

    private suspend fun fetchTransactions(accountId: String?, searchKeyword: String, types: List<String>) {
        val transactions = when {
            types.isEmpty() && searchKeyword.isEmpty() -> {
                getAllTransactionsListItemsUseCase(accountId)
            }
            types.isNotEmpty() && searchKeyword.isNotEmpty() -> {
                getAllTransactionsByKeywordAndTypeUseCase(accountId, types, searchKeyword)
            }
            types.isNotEmpty() && searchKeyword.isEmpty() -> {
                getTransactionsByTypeUseCase(accountId, types)
            }
            else -> {
                getTransactionByKeywordUseCase(accountId, searchKeyword)
            }
        }.map { it.toTransactionDateItemUI() }

        _state.update { it.copy(transactions = transactions) }
    }


    private fun getTransactionById(id: String) {
        viewModelScope.launch {
            _state.update { it.copy(transactionUiState = UiState.Loading) }
            val transaction = getTransactionByIdUseCase.invoke(id)
            delay(500L)
            if (transaction != null) {
                _state.update { it.copy(transactionUiState = UiState.Success(transaction)) }
            }
        }
    }

    private fun toggleSearchTextVisibility() {
        _state.update { it.copy(isSearchTextVisible = !_state.value.isSearchTextVisible) }
    }

    private fun setSearchText(text: String) {
        _state.update { it.copy(searchParametersState = it.searchParametersState.copy(searchText = text)) }
    }

    private fun setTransactionType(transactionType: TransactionType) {
        _state.update { currentState ->
            val newSelection = currentState.selectedTransactionTypes.toMutableMap()

            val isSelected = newSelection[transactionType] ?: false
            newSelection[transactionType] = !isSelected

            currentState.copy(
                selectedTransactionTypes = newSelection,
                searchParametersState = currentState.searchParametersState.copy(selectedTransactionTypes = newSelection.filter { it.value }.map { it.key.name })
            )
        }
    }

    private fun showBottomSheet() {
        _state.update { it.copy(showBottomSheet = true, transactionUiState = UiState.Loading) }
    }

    private fun hideBottomSheet() {
        _state.update { it.copy(showBottomSheet = false, transactionUiState = UiState.Empty) }
    }
}