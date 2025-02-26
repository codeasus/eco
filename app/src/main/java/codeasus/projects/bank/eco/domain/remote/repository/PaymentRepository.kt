package codeasus.projects.bank.eco.domain.remote.repository

import codeasus.projects.bank.eco.domain.remote.model.payment.PaymentModel
import codeasus.projects.bank.eco.domain.remote.model.response.ApiResponse

interface PaymentRepository {
    suspend fun sendPayment(payment: PaymentModel): ApiResponse<String>
}