package codeasus.projects.bank.eco.domain.local.repository.customer

import codeasus.projects.bank.eco.domain.local.model.customer.CustomerModel

interface CustomerRepository {
    suspend fun saveCustomer(customer: CustomerModel)
    suspend fun getCustomerByName(name: String): CustomerModel?
    suspend fun getAllCustomers(): List<CustomerModel>
    suspend fun deleteCustomers()
}