package codeasus.projects.bank.eco.domain.remote.repository.banking

import codeasus.projects.bank.eco.domain.remote.model.banking.BinLookupModel
import codeasus.projects.bank.eco.domain.utils.DomainResult

interface BankAccountRepository {
    suspend fun getAccountDetailsByBin(bin: String): DomainResult<BinLookupModel>
}