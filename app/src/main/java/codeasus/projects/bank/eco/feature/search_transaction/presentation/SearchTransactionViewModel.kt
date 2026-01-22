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
        loadTransactionsBasedOnTransactionType()
        loadTransactionsByKeyword()
    }

    fun handleIntent(intent: SearchTransactionIntent) {
        when (intent) {
            is SearchTransactionIntent.ToggleSearchTextVisibility -> toggleSearchTextVisibility()
            is SearchTransactionIntent.SetSearchText -> setSearchText(intent.text)
            is SearchTransactionIntent.SelectTransactionType -> setTransactionType(intent.type)
            is SearchTransactionIntent.ShowBottomSheet -> {
                showBottomSheet()
                loadTransactionById(intent.transactionId)
            }

            is SearchTransactionIntent.HideBottomSheet -> {
                hideBottomSheet()
            }
        }
    }

    private fun toggleSearchTextVisibility() {
        _state.value = _state.value.copy(isSearchTextVisible = !_state.value.isSearchTextVisible)
    }

    private fun setSearchText(text: String) {
        _state.value = _state.value.copy(searchText = text)
    }

    private fun setTransactionType(transactionType: TransactionType) {
        _state.value = _state.value.copy(
            selectedTransactionTypes = _state.value.selectedTransactionTypes.toMutableMap().apply {
                put(transactionType, !(this[transactionType] ?: false))
            }
        )
        loadTransactionsBasedOnTransactionType()
    }

    private fun getSelectedTransactionTypes(): List<String> {
        return _state.value.selectedTransactionTypes.filter { it.value }.map { it.key.name }
    }

    private fun loadTransactionById(id: String) {
        viewModelScope.launch {
            val transaction = getTransactionByIdUseCase.invoke(id)
            delay(500L)
            if (transaction != null) {
                _state.emit(_state.value.copy(transactionUiState = UiState.Success(transaction)))
            }
        }
    }

    private fun getAllTransactions() {
        viewModelScope.launch {
            val transactions = getAllTransactionsListItemsUseCase.invoke().map { it.toTransactionDateItemUI() }
            _state.value = _state.value.copy(transactions = transactions)
        }
    }

    private fun getTransactionByType(types: List<String>) {
        viewModelScope.launch {
            val transactions = getTransactionsByTypeUseCase.invoke(types = types).map { it.toTransactionDateItemUI() }
            _state.value = _state.value.copy(transactions = transactions)
        }
    }

    private fun loadTransactionsBasedOnTransactionType() {
        viewModelScope.launch {
            _state.collectLatest { searchTransactionState ->
                val types = searchTransactionState.selectedTransactionTypes
                    .filter { it.value }
                    .map { it.key.name }
                val searchKeyword = searchTransactionState.searchText
                when {
                    types.isEmpty() && searchKeyword.isEmpty() -> {
                        getAllTransactions()
                    }

                    types.isNotEmpty() && searchKeyword.isNotEmpty() -> {
                        val transactions = getAllTransactionsByKeywordAndTypeUseCase.invoke(searchKeyword = searchKeyword, types = types).map {it.toTransactionDateItemUI()}
                        _state.value = _state.value.copy(transactions = transactions)
                    }

                    types.isNotEmpty() && searchKeyword.isEmpty() -> {
                        getTransactionByType(types = types)
                    }

                    types.isEmpty() && searchKeyword.isNotEmpty() -> {
                        val transactions = getTransactionByKeywordUseCase.invoke(searchKeyword = searchKeyword).map { it.toTransactionDateItemUI() }
                        _state.value = _state.value.copy(transactions = transactions)
                    }
                }
            }
        }
    }

    private fun loadTransactionsByKeyword() {
        viewModelScope.launch {
            _state.collectLatest { uiSearchState ->
                delay(1000L)

                if (uiSearchState.searchText.isEmpty() || !uiSearchState.isSearchTextVisible) {
                    loadTransactionsBasedOnTransactionType()
                    return@collectLatest
                }

                val types = getSelectedTransactionTypes()
                val searchKeyword = uiSearchState.searchText

                val transactions = if (types.isEmpty()) {
                    getTransactionByKeywordUseCase.invoke(searchKeyword = searchKeyword)
                } else {
                    getAllTransactionsByKeywordAndTypeUseCase.invoke(searchKeyword = searchKeyword, types = types)
                }.map { it.toTransactionDateItemUI() }

                _state.value = _state.value.copy(transactions = transactions)
            }
        }
    }

    private fun showBottomSheet() {
        _state.value = _state.value.copy(showBottomSheet = true, transactionUiState = UiState.Loading)
    }

    private fun hideBottomSheet() {
        _state.value = _state.value.copy(showBottomSheet = false, transactionUiState = UiState.Empty)
    }
}