package codeasus.projects.bank.eco.feature.product.presentation

import androidx.lifecycle.viewModelScope
import codeasus.projects.bank.eco.core.ui.shared.viewmodel.base.BaseViewModel
import codeasus.projects.bank.eco.domain.local.repository.user.UserRepository
import codeasus.projects.bank.eco.feature.product.presentation.states.ProductIntent
import codeasus.projects.bank.eco.feature.product.presentation.states.ProductState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(userRepository: UserRepository) : BaseViewModel(userRepository) {
    private val _state = MutableStateFlow(ProductState())
    val state = _state.asStateFlow()

    init {
        loadUser()
    }

    fun handleIntent(intent: ProductIntent) {

    }

    private fun loadUser() {
        viewModelScope.launch {
            user.collect {  user ->
                if(user != null) {
                    _state.emit(_state.value.copy(user = user))
                }
            }
        }
    }
}