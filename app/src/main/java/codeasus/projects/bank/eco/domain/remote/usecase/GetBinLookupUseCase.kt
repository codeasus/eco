package codeasus.projects.bank.eco.domain.remote.usecase

import codeasus.projects.bank.eco.domain.remote.model.banking.BinLookupModel
import codeasus.projects.bank.eco.domain.remote.repository.banking.BankingRepository
import codeasus.projects.bank.eco.domain.utils.DomainResult
import codeasus.projects.bank.eco.domain.utils.UseCase
import javax.inject.Inject

class GetBinLookupUseCase @Inject constructor(private val repository: BankingRepository) : UseCase<String, DomainResult<BinLookupModel>> {
    override suspend fun invoke(data: String): DomainResult<BinLookupModel> {
        return repository.getAccountDetailsByBin(data)
    }
}
