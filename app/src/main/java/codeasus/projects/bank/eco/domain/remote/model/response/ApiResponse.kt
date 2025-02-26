package codeasus.projects.bank.eco.domain.remote.model.response

sealed class ApiResponse<out T> {
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Error(val errorCode: Int, val errorMessage: String) : ApiResponse<Nothing>()
    data object Loading : ApiResponse<Nothing>()
}