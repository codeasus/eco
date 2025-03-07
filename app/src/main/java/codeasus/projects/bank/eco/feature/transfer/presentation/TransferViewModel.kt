package codeasus.projects.bank.eco.feature.transfer.presentation

import codeasus.projects.bank.eco.core.ui.shared.viewmodel.base.BaseViewModel
import codeasus.projects.bank.eco.domain.local.repository.customer.CustomerRepository
import codeasus.projects.bank.eco.domain.local.repository.transaction.TransactionRepository
import codeasus.projects.bank.eco.domain.local.repository.user.UserRepository
import codeasus.projects.bank.eco.domain.remote.repository.PaymentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor(
    userRepository: UserRepository,
    customerRepository: CustomerRepository,
    val transactionRepository: TransactionRepository,
    private val paymentRepository: PaymentRepository
) :
    BaseViewModel(userRepository, transactionRepository)