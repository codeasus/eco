package codeasus.projects.bank.eco.data.remote.repository

import codeasus.projects.bank.eco.data.remote.mappers.ApiErrorMapper
import codeasus.projects.bank.eco.data.remote.mappers.BinLookupMapper
import codeasus.projects.bank.eco.data.remote.model.BinLookupResponseDto
import codeasus.projects.bank.eco.domain.remote.model.banking.BinLookupModel
import codeasus.projects.bank.eco.domain.remote.repository.banking.BankAccountRepository
import codeasus.projects.bank.eco.domain.utils.DomainResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header

class BankAccountRemoteRepository(private val httpClient: HttpClient): BankAccountRepository {

    companion object {
        private const val BASE_URL = "https://data.handyapi.com/bin"
    }

    override suspend fun getAccountDetailsByBin(bin: String): DomainResult<BinLookupModel> {
        return try {
            val dto: BinLookupResponseDto = httpClient.get("$BASE_URL/$bin") {
                header("x-api-key", "PUB-0YL5bYB3LRMJKk9Dt72pTzvj8q")
            }.body()
            DomainResult.Success(BinLookupMapper.toDomain(dto))
        } catch (e: Exception) {
            DomainResult.Error(ApiErrorMapper.toDomainError(e))
        }
    }
}