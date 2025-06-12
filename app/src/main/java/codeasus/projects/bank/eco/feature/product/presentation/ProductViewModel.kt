package codeasus.projects.bank.eco.feature.product.presentation

import codeasus.projects.bank.eco.core.ui.shared.viewmodel.base.BaseViewModel
import codeasus.projects.bank.eco.domain.local.repository.user.UserRepository
import codeasus.projects.bank.eco.feature.product.presentation.states.ProductIntent
import codeasus.projects.bank.eco.feature.product.presentation.states.ProductState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(userRepository: UserRepository) : BaseViewModel(userRepository) {
    private val _state = MutableStateFlow(ProductState())
    val state = _state.asStateFlow()

    fun handleIntent(intent: ProductIntent) {

    }
}