package codeasus.projects.bank.eco.domain.local.repository.customer

import codeasus.projects.bank.eco.domain.local.model.customer.CustomerModel
import kotlinx.coroutines.flow.Flow

interface CustomerRepository {
    suspend fun saveCustomer(customer: CustomerModel)
    suspend fun getCustomerByName(name: String): CustomerModel?
    fun getAllCustomers(): Flow<List<CustomerModel>>
}