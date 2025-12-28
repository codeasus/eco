package codeasus.projects.bank.eco.core.ui.shared.viewmodel.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import codeasus.projects.bank.eco.core.ui.shared.mappers.toUserUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.UserUi
import codeasus.projects.bank.eco.domain.local.repository.user.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _user = MutableStateFlow<UserUi?>(null)
    val user: StateFlow<UserUi?> = _user.asStateFlow()

    init {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {
            try {
                _user.emit(userRepository.loadUser()?.toUserUi())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}