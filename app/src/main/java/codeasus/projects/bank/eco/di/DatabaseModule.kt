package codeasus.projects.bank.eco.di

import android.content.Context
import androidx.room.Room
import codeasus.projects.bank.eco.core.database.AppDatabase
import codeasus.projects.bank.eco.data.local.dao.BankAccountDao
import codeasus.projects.bank.eco.data.local.dao.CustomerDao
import codeasus.projects.bank.eco.data.local.dao.SystemMessageDao
import codeasus.projects.bank.eco.data.local.dao.TransactionDao
import codeasus.projects.bank.eco.data.local.repository.customer.CustomerRepositoryImpl
import codeasus.projects.bank.eco.data.local.repository.system_message.SystemMessageRepositoryImpl
import codeasus.projects.bank.eco.data.local.repository.transaction.TransactionRepositoryImpl
import codeasus.projects.bank.eco.data.local.repository.user.BankAccountRepositoryLocal
import codeasus.projects.bank.eco.domain.local.repository.customer.CustomerRepository
import codeasus.projects.bank.eco.domain.local.repository.system_message.SystemMessageRepository
import codeasus.projects.bank.eco.domain.local.repository.transaction.TransactionRepository
import codeasus.projects.bank.eco.domain.local.repository.user.BankAccountRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "banking_database"
        )
            .build()
    }

    @Provides
    fun provideCustomerDao(database: AppDatabase): CustomerDao {
        return database.customerDao()
    }

    @Provides
    fun provideTransactionDao(database: AppDatabase): TransactionDao {
        return database.transactionDao()
    }

    @Provides
    fun provideSystemMessageDao(database: AppDatabase): SystemMessageDao {
        return database.systemMessageDao()
    }

    @Provides
    fun provideBankAccountDao(database: AppDatabase): BankAccountDao {
        return database.bankAccountDao()
    }

    @Provides
    @Singleton
    fun provideCustomerRepository(customerDao: CustomerDao): CustomerRepository {
        return CustomerRepositoryImpl(customerDao)
    }

    @Provides
    @Singleton
    fun provideTransactionRepository(transactionDao: TransactionDao): TransactionRepository {
        return TransactionRepositoryImpl(transactionDao)
    }

    @Provides
    @Singleton
    fun provideSystemMessageRepository(systemMessageDao: SystemMessageDao): SystemMessageRepository {
        return SystemMessageRepositoryImpl(systemMessageDao)
    }

    @Provides
    @Singleton
    fun provideBankAccountRepository(bankAccountDao: BankAccountDao): BankAccountRepository {
        return BankAccountRepositoryLocal(bankAccountDao)
    }
}