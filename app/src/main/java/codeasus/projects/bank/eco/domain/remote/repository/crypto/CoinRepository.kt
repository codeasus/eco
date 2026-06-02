package codeasus.projects.bank.eco.domain.remote.repository.crypto

import codeasus.projects.bank.eco.domain.remote.model.crypto.Coin
import codeasus.projects.bank.eco.domain.remote.model.crypto.CoinDetail
import codeasus.projects.bank.eco.domain.remote.model.crypto.PricePoint
import codeasus.projects.bank.eco.domain.utils.DomainResult

interface CoinRepository {
    suspend fun searchCoin(query: String, currency: String): DomainResult<Coin?>
    suspend fun getCoinsList(currency: String = "usd", page: Int, coinsPerPage: Int): DomainResult<List<Coin>>
    suspend fun getMyCoinList(coinIds: List<String>, currency: String = "usd"): DomainResult<List<Coin>>
    suspend fun getMyCoinList(coinIds: List<String>, currency: String = "usd", fromTimestampSeconds: Long, toTimestampSeconds: Long): DomainResult<List<Coin>>
    suspend fun getMyCoinAmount(): Map<String, Double>
    suspend fun getCoinDetails(id: String): DomainResult<CoinDetail>
    suspend fun getCoinHistory(id: String, currency: String = "usd", fromTimestampSeconds: Long, toTimestampSeconds: Long): DomainResult<List<PricePoint>>
}