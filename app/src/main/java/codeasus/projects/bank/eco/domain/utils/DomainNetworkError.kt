package codeasus.projects.bank.eco.domain.utils

sealed class DomainNetworkError(val message: String): DomainError {
    data object NetworkUnavailable : DomainNetworkError("Network connection unavailable")
    data object ServiceTimeout : DomainNetworkError("Service request timed out")
    data object ResourceNotFound : DomainNetworkError("Requested resource not found")
    data object ServiceUnavailable : DomainNetworkError("Service temporarily unavailable")
    data object RateLimitExceeded : DomainNetworkError("Too many requests, please try again later")
    data object UnauthorizedAccess : DomainNetworkError("Authentication required")
    data class UnknownError(val details: String) : DomainNetworkError("An unexpected error occurred: $details")
}