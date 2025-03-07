package codeasus.projects.bank.eco.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import codeasus.projects.bank.eco.data.local.entity.CustomerEntity

@Dao
interface CustomerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomer(customer: CustomerEntity)

    @Query("SELECT * FROM customers WHERE name = :name")
    suspend fun getCustomerByName(name: String): CustomerEntity?

    @Query("SELECT * FROM customers")
    suspend fun getAllCustomers(): List<CustomerEntity>

    @Query("DELETE FROM customers")
    suspend fun deleteCustomers()
}