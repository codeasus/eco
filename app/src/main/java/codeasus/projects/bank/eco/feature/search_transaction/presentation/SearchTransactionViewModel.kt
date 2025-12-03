package codeasus.projects.bank.eco.feature.search_transaction.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import codeasus.projects.bank.eco.core.ui.shared.mappers.toCustomerUi
import codeasus.projects.bank.eco.core.ui.shared.mappers.toTransactionUi
import codeasus.projects.bank.eco.domain.local.model.enums.TransactionType
import codeasus.projects.bank.eco.domain.local.repository.transaction.TransactionRepository
import codeasus.projects.bank.eco.feature.search_transaction.states.SearchTransactionIntent
import codeasus.projects.bank.eco.feature.search_transaction.states.SearchTransactionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchTransactionViewModel @Inject constructor(private val transactionRepository: TransactionRepository) : ViewModel() {
    private val _state = MutableStateFlow(SearchTransactionState())
    val state = _state.asStateFlow()

    init {
        loadTransactionsBasedOnTransactionType()
        loadTransactionsByKeyword()
    }

    fun handleIntent(intent: SearchTransactionIntent) {
        when(intent) {
            is SearchTransactionIntent.ToggleSearchTextVisibility -> toggleSearchTextVisibility()
            is SearchTransactionIntent.SetSearchText -> setSearchText(intent.text)
            is SearchTransactionIntent.SelectTransactionType -> setTransactionType(intent.type)
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

    private fun getAllTransactions() {
        viewModelScope.launch {
            val transactions = transactionRepository
                .getAllTransactions(cardNumber = null)
                .map { Pair(it.value.toCustomerUi(), it.key.toTransactionUi()) }
            _state.value = _state.value.copy(transactions = transactions)
        }
    }

    private fun getTransactionByType(types: List<String>) {
        viewModelScope.launch {
            val transactions = transactionRepository
                .getTransactionsByType(cardNumber = null, types = types)
                .map { Pair(it.value.toCustomerUi(), it.key.toTransactionUi()) }
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
                        val transactions = transactionRepository
                            .getTransactionsByKeywordAndType(
                                cardNumber = null,
                                keyword = searchKeyword,
                                types = types
                            )
                            .map { Pair(it.value.toCustomerUi(), it.key.toTransactionUi()) }
                        _state.value = _state.value.copy(transactions = transactions)
                    }

                    types.isNotEmpty() && searchKeyword.isEmpty() -> {
                        getTransactionByType(types = types)
                    }

                    types.isEmpty() && searchKeyword.isNotEmpty() -> {
                        val transactions = transactionRepository
                            .getTransactionsByKeyword(
                                cardNumber = null,
                                keyword = searchKeyword
                            ).map { Pair(it.value.toCustomerUi(), it.key.toTransactionUi()) }
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
                val searchText = uiSearchState.searchText

                val transactions = if (types.isEmpty()) {
                    transactionRepository.getTransactionsByKeyword(
                        cardNumber = null,
                        keyword = searchText
                    )
                } else {
                    transactionRepository.getTransactionsByKeywordAndType(
                        cardNumber = null,
                        keyword = searchText,
                        types = types
                    )
                }.map { Pair(it.value.toCustomerUi(), it.key.toTransactionUi()) }

                _state.value = _state.value.copy(transactions = transactions)
            }
        }
    }
}