package codeasus.projects.bank.eco.core.ui.shared.viewmodel.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import codeasus.projects.bank.eco.domain.local.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userState = MutableStateFlow<codeasus.projects.bank.eco.domain.local.model.user.UserModel?>(null)
    val userState: StateFlow<codeasus.projects.bank.eco.domain.local.model.user.UserModel?> = _userState.asStateFlow()

    init {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {
            try {
                val user = userRepository.loadUser()
                _userState.value = user
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun saveUser(user: codeasus.projects.bank.eco.domain.local.model.user.UserModel) {
        viewModelScope.launch {
            try {
                userRepository.saveUser(user)
                _userState.value = user
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}