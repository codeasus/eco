package codeasus.projects.bank.eco.domain.local.usecase

import codeasus.projects.bank.eco.core.ui.shared.mappers.toCustomerUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.CustomerUi
import codeasus.projects.bank.eco.domain.local.repository.customer.CustomerRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class GetFriendsUseCase @Inject constructor(private val customerRepository: CustomerRepository) {
    suspend operator fun invoke(formattedCardNumber: Boolean = true): List<CustomerUi> {
        val friends = customerRepository.getFriends().map { it.toCustomerUi(formattedCardNumber) }
        delay(1200L)
        return friends
    }
}