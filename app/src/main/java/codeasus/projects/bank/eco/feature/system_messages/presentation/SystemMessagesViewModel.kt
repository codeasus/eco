package codeasus.projects.bank.eco.feature.system_messages.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import codeasus.projects.bank.eco.domain.local.model.enums.Priority
import codeasus.projects.bank.eco.domain.local.repository.system_message.SystemMessageRepository
import codeasus.projects.bank.eco.feature.system_messages.states.SystemMessagesIntent
import codeasus.projects.bank.eco.feature.system_messages.states.SystemMessagesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SystemMessagesViewModel @Inject constructor(private val systemMessageRepository: SystemMessageRepository) :
    ViewModel() {
    private val _state = MutableStateFlow(SystemMessagesState())
    val state = _state.asStateFlow()

    init {
        handleIntent(SystemMessagesIntent.GetAllSystemMessages)
    }

    fun handleIntent(intent: SystemMessagesIntent) {
        when (intent) {
            is SystemMessagesIntent.GetAllSystemMessages -> getAllSystemMessages()
            is SystemMessagesIntent.FilterByPriority -> filterSystemMessagesByPriority(intent.priority)
            is SystemMessagesIntent.DeleteSystemMessages -> TODO()
        }
    }

    private fun getAllSystemMessages() {
        viewModelScope.launch {
            val systemMessages = systemMessageRepository.getAllSystemMessages()
            _state.emit(_state.value.copy(systemMessages = systemMessages))
        }
    }

    private fun filterSystemMessagesByPriority(priority: Priority) {
        viewModelScope.launch {
            if(priority == _state.value.selectedPriority) {
                _state.emit(_state.value.copy(selectedPriority = null))
                getAllSystemMessages()
                return@launch
            }
            val filteredSystemMessages = systemMessageRepository.getSystemMessagesByPriority(priority)
            _state.emit(
                _state.value.copy(systemMessages = filteredSystemMessages, selectedPriority = priority)
            )
        }
    }
}
