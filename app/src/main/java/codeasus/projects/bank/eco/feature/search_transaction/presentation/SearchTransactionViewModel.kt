package codeasus.projects.bank.eco.feature.search_transaction.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import codeasus.projects.bank.eco.domain.local.model.customer.CustomerModel
import codeasus.projects.bank.eco.domain.local.model.enums.TransactionType
import codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel
import codeasus.projects.bank.eco.domain.local.repository.transaction.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchTransactionViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    val uiSearchState = MutableStateFlow(SearchTransactionState())
    val uiSelectedTransactionTypeState = MutableStateFlow(TransactionType.toDefaultSelectionMap())

    private val _transactions = MutableStateFlow<List<Pair<CustomerModel, TransactionModel>>>(emptyList())
    val transactions: StateFlow<List<Pair<CustomerModel, TransactionModel>>> = _transactions

    init {
        loadTransactionsBasedOnTransactionType()
        loadTransactionsByKeyword()
    }

    fun toggleSearchTextVisibility() {
        uiSearchState.value =
            uiSearchState.value.copy(isSearchTextVisible = !uiSearchState.value.isSearchTextVisible)
    }

    fun setSearchText(text: String) {
        uiSearchState.value = uiSearchState.value.copy(searchText = text)
    }

    fun searchActionEngaged() {
        uiSearchState.value = uiSearchState.value.copy(searchActionEngaged = true)
    }

    fun setTransactionType(transactionType: TransactionType) {
        uiSelectedTransactionTypeState.value = uiSelectedTransactionTypeState.value
            .toMutableMap()
            .apply { put(transactionType, !(this[transactionType] ?: false)) }
        loadTransactionsBasedOnTransactionType()
    }

    fun getTransactionTypeSelectState(transactionType: TransactionType): Boolean {
        return uiSelectedTransactionTypeState.value[transactionType] ?: false
    }

    private fun getSelectedTransactionTypes(): List<String> {
        return uiSelectedTransactionTypeState.value.filter { it.value }.map { it.key.name }
    }

    private fun getSearchText(): String {
        return uiSearchState.value.searchText
    }

    private fun loadTransactionsBasedOnTransactionType() {
        viewModelScope.launch {
            uiSelectedTransactionTypeState.collectLatest { uiSelectedTransactionTypeState ->
                val types = uiSelectedTransactionTypeState.filter { it.value }.map { it.key.name }
                val searchKeyword = getSearchText()
                when {
                    types.isEmpty() && searchKeyword.isEmpty() -> {
                        getAllTransactions()
                    }

                    types.isNotEmpty() && searchKeyword.isNotEmpty() -> {
                        val transactions = transactionRepository.getTransactionsByKeywordAndType(
                            cardNumber = null,
                            keyword = searchKeyword,
                            types = types
                        ).map { Pair(it.value, it.key) }
                        _transactions.emit(transactions)
                    }

                    types.isNotEmpty() && searchKeyword.isEmpty() -> {
                        getTransactionByType(types = types)
                    }

                    types.isEmpty() && searchKeyword.isNotEmpty() -> {
                        val transactions = transactionRepository.getTransactionsByKeyword(
                            cardNumber = null,
                            keyword = searchKeyword
                        ).map { Pair(it.value, it.key) }
                        _transactions.emit(transactions)
                    }
                }
            }
        }
    }

    private fun getAllTransactions() {
        viewModelScope.launch {
            val transactions = transactionRepository.getAllTransactions(cardNumber = null).map { Pair(it.value, it.key) }
            _transactions.emit(transactions)
        }
    }

    private fun getTransactionByType(types: List<String>) {
        viewModelScope.launch {
            val transactions = transactionRepository.getTransactionsByType(cardNumber = null, types = types).map { Pair(it.value, it.key) }
            _transactions.emit(transactions)
        }
    }

    private fun loadTransactionsByKeyword() {
        viewModelScope.launch {
            uiSearchState.collectLatest { uiSearchState ->
                delay(1000L)

                if (uiSearchState.searchText.isEmpty() || !uiSearchState.isSearchTextVisible) {
                    loadTransactionsBasedOnTransactionType()
                    return@collectLatest
                }

                val types = getSelectedTransactionTypes()
                val searchText = uiSearchState.searchText

                val transactions = if (types.isEmpty()) {
                    transactionRepository.getTransactionsByKeyword(cardNumber = null, keyword = searchText)
                } else {
                    transactionRepository.getTransactionsByKeywordAndType(
                        cardNumber = null,
                        keyword = searchText,
                        types = types
                    )
                }.map { Pair(it.value, it.key) }

                _transactions.emit(transactions)
            }
        }
    }
}