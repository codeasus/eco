package codeasus.projects.bank.eco.data.remote.mappers

import codeasus.projects.bank.eco.domain.utils.DomainError
import codeasus.projects.bank.eco.domain.utils.DomainNetworkError
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import java.net.UnknownHostException

object ApiErrorMapper {
    fun toDomainError(throwable: Throwable): DomainError {
        return when (throwable) {
            is ConnectTimeoutException, is SocketTimeoutException -> DomainNetworkError.ServiceTimeout
            is UnknownHostException -> DomainNetworkError.NetworkUnavailable
            is ClientRequestException -> when (throwable.response.status.value) {
                404 -> DomainNetworkError.ResourceNotFound
                401 -> DomainNetworkError.UnauthorizedAccess
                429 -> DomainNetworkError.RateLimitExceeded
                else -> DomainNetworkError.UnknownError("Client error: ${throwable.response.status}")
            }

            is ServerResponseException -> when (throwable.response.status.value) {
                503 -> DomainNetworkError.ServiceUnavailable
                else -> DomainNetworkError.UnknownError("Server error: ${throwable.response.status}")
            }

            else -> DomainNetworkError.UnknownError(throwable.message ?: "Unknown error occurred")
        }
    }
}