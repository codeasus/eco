package codeasus.projects.bank.eco.domain.local.repository.user

import codeasus.projects.bank.eco.domain.local.model.user.UserBankAccountModel

interface BankAccountRepository  {
    suspend fun insertBankAccount(account: UserBankAccountModel)
    suspend fun getBankAccountForPublicById(accountId: String): UserBankAccountModel?
    suspend fun getBankAccountForPrivateById(accountId: String): UserBankAccountModel?
    suspend fun getBankAccounts(): List<UserBankAccountModel>
    suspend fun deleteBankAccount(accountId: String)
    suspend fun deleteBankAccounts()
}