package codeasus.projects.bank.eco.data.remote.repository

import codeasus.projects.bank.eco.domain.remote.model.payment.PaymentModel
import codeasus.projects.bank.eco.domain.remote.model.response.ApiResponse
import codeasus.projects.bank.eco.domain.remote.repository.PaymentRepository

class MockPaymentRepository : PaymentRepository {
    private val random = java.util.Random()

    private suspend fun simulateNetworkDelay() {
        val delay = random.nextInt(500) + 100L // 100-600ms delay
        kotlinx.coroutines.delay(delay)
    }

    private fun generateTransactionId(): String {
        return java.util.UUID.randomUUID().toString()
    }

    override suspend fun sendPayment(payment: PaymentModel): ApiResponse<String> {
        simulateNetworkDelay()
        if (random.nextInt(10) == 0) {
            return ApiResponse.Error(500, "Network connection failed")
        }
        if (payment.amount <= 0) {
            return ApiResponse.Error(400, "Invalid payment amount")
        }
        if (payment.fromBankAccountNumber.isBlank() || payment.toBankAccountNumber.isBlank()) {
            return ApiResponse.Error(400, "Bank account numbers cannot be empty")
        }
        val transactionId = generateTransactionId()
        return ApiResponse.Success(transactionId)
    }
}