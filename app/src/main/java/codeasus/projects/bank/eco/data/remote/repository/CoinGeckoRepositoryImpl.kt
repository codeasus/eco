package codeasus.projects.bank.eco.data.remote.repository

import codeasus.projects.bank.eco.data.remote.mappers.toDomain
import codeasus.projects.bank.eco.data.remote.model.crypto.CoinDetailDto
import codeasus.projects.bank.eco.data.remote.model.crypto.CoinMarketDto
import codeasus.projects.bank.eco.data.remote.model.crypto.MarketChartDto
import codeasus.projects.bank.eco.data.remote.model.crypto.SearchResponseDto
import codeasus.projects.bank.eco.domain.remote.model.crypto.Coin
import codeasus.projects.bank.eco.domain.remote.model.crypto.CoinDetail
import codeasus.projects.bank.eco.domain.remote.model.crypto.PricePoint
import codeasus.projects.bank.eco.domain.remote.repository.crypto.CoinRepository
import codeasus.projects.bank.eco.domain.utils.DomainResult
import codeasus.projects.bank.eco.domain.utils.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class CoinGeckoRepositoryImpl(private val client: HttpClient) : CoinRepository {

    private fun HttpRequestBuilder.setup(endpoint: String) {
        url("https://api.coingecko.com/api/v3/$endpoint")
        header("x-cg-demo-api-key", "CG-R4TRgwQZmYKbkZScKToeA1z8")
    }

    override suspend fun searchCoin(query: String, currency: String): DomainResult<Coin?> = safeApiCall {
        val searchResponse: SearchResponseDto = client.get {
            setup("search")
            parameter("query", query)
        }.body()

        val matchedCoin = searchResponse.coins.firstOrNull() ?: return@safeApiCall null

        val marketResponse: List<CoinMarketDto> = client.get {
            setup("coins/markets")
            parameter("vs_currency", currency)
            parameter("ids", matchedCoin.id)
        }.body()

        marketResponse.firstOrNull()?.toDomain()
    }

    override suspend fun getMyCoinList(coinIds: List<String>, currency: String): DomainResult<List<Coin>> = safeApiCall{
        val idsString = coinIds.joinToString(",")

        val response: List<CoinMarketDto> =  client.get {
            setup("coins/markets")
            parameter("vs_currency", currency)
            parameter("order", "market_cap_desc")
            parameter("price_change_percentage", "1h,7d")
            parameter("ids", idsString)
            parameter("sparkline", false)
        }.body()

        response.map { it.toDomain() }
    }

    override suspend fun getMyCoinList(coinIds: List<String>, currency: String, fromTimestampSeconds: Long, toTimestampSeconds: Long): DomainResult<List<Coin>> = safeApiCall{
        val idsString = coinIds.joinToString(",")

        val marketResponse: List<CoinMarketDto> =  client.get {
            setup("coins/markets")
            parameter("vs_currency", currency)
            parameter("order", "market_cap_desc")
            parameter("price_change_percentage", "1h,7d")
            parameter("ids", idsString)
            parameter("sparkline", false)
        }.body()

       val domainPriceFluctuationHistory =  coroutineScope {
            coinIds.map {
                async {
                    val historyResponse: MarketChartDto = client.get {
                        setup("coins/${it}/market_chart/range")
                        parameter("vs_currency", currency)
                        parameter("from", fromTimestampSeconds)
                        parameter("to", toTimestampSeconds)
                    }.body()
                    historyResponse.toDomain()
                }
            }.awaitAll()
        }

        marketResponse.mapIndexed { index, dto -> dto.toDomain(domainPriceFluctuationHistory[index]) }
    }

    override suspend fun getMyCoinAmount(): Map<String, Double> {
        return mapOf("bitcoin" to 1.6, "ethereum" to 2.3, "tether" to 4.5)
    }

    override suspend fun getCoinsList(currency: String, page: Int, coinsPerPage: Int): DomainResult<List<Coin>> = safeApiCall {
        val response: List<CoinMarketDto> = client.get {
            setup("coins/markets")
            parameter("vs_currency", currency)
            parameter("order", "market_cap_desc")
            parameter("price_change_percentage", "1h,7d")
            parameter("per_page", coinsPerPage)
            parameter("page", page)
            parameter("sparkline", false)
        }.body()

        response.map { it.toDomain() }
    }

    override suspend fun getCoinDetails(id: String): DomainResult<CoinDetail> = safeApiCall {
        val response: CoinDetailDto = client.get {
            setup("coins/$id")
            parameter("localization", false)
            parameter("tickers", false)
            parameter("community_data", false)
            parameter("developer_data", false)
        }.body()

        response.toDomain()
    }

    override suspend fun getCoinHistory(
        id: String,
        currency: String,
        fromTimestampSeconds: Long,
        toTimestampSeconds: Long
    ): DomainResult<List<PricePoint>> = safeApiCall {
        val response: MarketChartDto = client.get {
            setup("coins/$id/market_chart/range")
            parameter("vs_currency", currency)
            parameter("from", fromTimestampSeconds)
            parameter("to", toTimestampSeconds)
        }.body()

        response.toDomain()
    }
}