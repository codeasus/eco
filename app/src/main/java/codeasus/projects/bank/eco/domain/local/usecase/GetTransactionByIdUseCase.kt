package codeasus.projects.bank.eco.domain.local.usecase

import codeasus.projects.bank.eco.core.ui.shared.mappers.toTransactionUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.TransactionUi
import codeasus.projects.bank.eco.domain.local.repository.transaction.TransactionRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class GetTransactionByIdUseCase  @Inject constructor(private val transactionRepository: TransactionRepository) {
    suspend operator fun invoke(id: String): TransactionUi? {
        val transaction = transactionRepository.getTransactionById(id)?.toTransactionUi(true)
        delay(500L)
        return transaction
    }
}