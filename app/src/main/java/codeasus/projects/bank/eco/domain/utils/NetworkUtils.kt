package codeasus.projects.bank.eco.domain.utils

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.util.network.UnresolvedAddressException

suspend fun <T> safeApiCall(block: suspend () -> T): DomainResult<T> {
    return try {
        DomainResult.Success(block())
    } catch (e: ClientRequestException) {
        val error = when (e.response.status.value) {
            401 -> DomainNetworkError.UnauthorizedAccess
            404 -> DomainNetworkError.ResourceNotFound
            429 -> DomainNetworkError.RateLimitExceeded
            else -> DomainNetworkError.UnknownError(e.message)
        }
        DomainResult.Error(error)
    } catch (e: ServerResponseException) {
        val error = when (e.response.status.value) {
            503 -> DomainNetworkError.ServiceUnavailable
            504 -> DomainNetworkError.ServiceTimeout
            else -> DomainNetworkError.UnknownError(e.message)
        }
        DomainResult.Error(error)
    } catch (e: HttpRequestTimeoutException) {
        DomainResult.Error(DomainNetworkError.ServiceTimeout)
    } catch (e: UnresolvedAddressException) {
        DomainResult.Error(DomainNetworkError.NetworkUnavailable)
    } catch (e: Exception) {
        DomainResult.Error(DomainNetworkError.UnknownError(e.message ?: "An unknown exception occurred"))
    }
}