package codeasus.projects.bank.eco.feature.transfer.presentation

import codeasus.projects.bank.eco.core.ui.shared.viewmodel.base.BaseViewModel
import codeasus.projects.bank.eco.domain.local.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor(userRepository: UserRepository) : BaseViewModel(userRepository)