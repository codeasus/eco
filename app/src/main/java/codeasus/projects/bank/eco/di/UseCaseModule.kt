package codeasus.projects.bank.eco.di

import codeasus.projects.bank.eco.domain.local.repository.customer.CustomerRepository
import codeasus.projects.bank.eco.domain.local.repository.transaction.TransactionRepository
import codeasus.projects.bank.eco.domain.local.usecase.GetAllTransactionsListItemsUseCase
import codeasus.projects.bank.eco.domain.local.usecase.GetFriendsUseCase
import codeasus.projects.bank.eco.domain.local.usecase.GetTransactionByIdUseCase
import codeasus.projects.bank.eco.domain.local.usecase.GetTransactionsByKeywordAndTypeUseCase
import codeasus.projects.bank.eco.domain.local.usecase.GetTransactionsByKeywordUseCase
import codeasus.projects.bank.eco.domain.local.usecase.GetTransactionsByTypeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetAllTransactionsListItemsUseCase(transactionRepository: TransactionRepository): GetAllTransactionsListItemsUseCase {
        return GetAllTransactionsListItemsUseCase(transactionRepository)
    }

    @Provides
    @Singleton
    fun provideGetTransactionsByTypeUseCase(transactionRepository: TransactionRepository): GetTransactionsByTypeUseCase {
        return GetTransactionsByTypeUseCase(transactionRepository)
    }

    @Provides
    @Singleton
    fun provideGetTransactionsByKeywordAndTypeUseCase(transactionRepository: TransactionRepository): GetTransactionsByKeywordAndTypeUseCase {
        return GetTransactionsByKeywordAndTypeUseCase(transactionRepository)
    }

    @Provides
    @Singleton
    fun provideGetTransactionsByKeywordUseCase(transactionRepository: TransactionRepository): GetTransactionsByKeywordUseCase {
        return GetTransactionsByKeywordUseCase(transactionRepository)
    }

    @Provides
    @Singleton
    fun provideGetTransactionByIdUseCase(transactionRepository: TransactionRepository): GetTransactionByIdUseCase {
        return GetTransactionByIdUseCase(transactionRepository)
    }

    @Provides
    @Singleton
    fun provideGetFriendsUseCase(customerRepository: CustomerRepository): GetFriendsUseCase {
        return GetFriendsUseCase(customerRepository)
    }
}