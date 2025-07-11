package codeasus.projects.bank.eco.feature.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import codeasus.projects.bank.eco.domain.local.repository.user.UserRepository
import codeasus.projects.bank.eco.feature.profile.presentation.states.ProfileIntent
import codeasus.projects.bank.eco.feature.profile.presentation.states.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    init {
        handleIntent(ProfileIntent.LoadUserData)
    }
    
    fun handleIntent(intent: ProfileIntent) {
        when(intent) {
            is ProfileIntent.LoadUserData -> loadUserData()

        }
    }

    private fun loadUserData() {
        viewModelScope.launch {
            val user = userRepository.loadUser()
            user?.let {
                _state.emit(state.value.copy(user = it))
            }
        }
    }
}