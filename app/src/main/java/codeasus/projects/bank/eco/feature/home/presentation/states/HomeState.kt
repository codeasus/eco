package codeasus.projects.bank.eco.feature.home.presentation.states

import codeasus.projects.bank.eco.domain.local.model.customer.CustomerModel
import codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel
import codeasus.projects.bank.eco.domain.local.model.user.UserBankAccountModel

data class HomeState(
    val isLoading: Boolean = false,
    val transactions: List<Pair<CustomerModel, TransactionModel>> = emptyList(),
    val cards: List<UserBankAccountModel> = emptyList(),
    val error: String? = null
)