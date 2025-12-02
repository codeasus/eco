package codeasus.projects.bank.eco.domain.local.repository.user

import codeasus.projects.bank.eco.domain.local.model.user.UserBankAccountModel

interface BankAccountRepository  {
    suspend fun insertBankAccount(account: UserBankAccountModel)
    suspend fun getBankAccountForPublicById(id: Long): UserBankAccountModel?
    suspend fun getBankAccountForPrivateById(id: Long): UserBankAccountModel?
    suspend fun getBankAccounts(): List<UserBankAccountModel>
    suspend fun deleteBankAccount(id: Long)
    suspend fun deleteBankAccounts()
}