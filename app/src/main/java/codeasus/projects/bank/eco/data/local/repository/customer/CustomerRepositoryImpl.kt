package codeasus.projects.bank.eco.data.local.repository.customer

import codeasus.projects.bank.eco.data.local.dao.CustomerDao
import codeasus.projects.bank.eco.data.local.mappers.toCustomerEntity
import codeasus.projects.bank.eco.data.local.mappers.toCustomerModel
import codeasus.projects.bank.eco.domain.local.model.customer.CustomerModel
import codeasus.projects.bank.eco.domain.local.repository.customer.CustomerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CustomerRepositoryImpl @Inject constructor(
    private val customerDao: CustomerDao
) : CustomerRepository {

    override suspend fun saveCustomer(customer: CustomerModel) {
        customerDao.insertCustomer(customer.toCustomerEntity())
    }

    override suspend fun getCustomerByName(name: String): CustomerModel? {
        return customerDao.getCustomerByName(name)?.toCustomerModel()
    }

    override fun getAllCustomers(): Flow<List<CustomerModel>> {
        return customerDao.getAllCustomers().map { entities ->
            entities.map { it.toCustomerModel() }
        }
    }
}