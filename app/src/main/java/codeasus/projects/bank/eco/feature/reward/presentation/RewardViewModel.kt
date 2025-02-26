package codeasus.projects.bank.eco.feature.reward.presentation

import codeasus.projects.bank.eco.core.ui.shared.viewmodel.base.BaseViewModel
import codeasus.projects.bank.eco.domain.local.repository.customer.CustomerRepository
import codeasus.projects.bank.eco.domain.local.repository.transaction.TransactionRepository
import codeasus.projects.bank.eco.domain.local.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RewardViewModel @Inject constructor(
    userRepository: UserRepository,
    customerRepository: CustomerRepository,
    transactionRepository: TransactionRepository
) :
    BaseViewModel(userRepository, customerRepository, transactionRepository)