package codeasus.projects.bank.eco.di

import android.content.Context
import codeasus.projects.bank.eco.data.local.repository.user.UserRepositoryImpl
import codeasus.projects.bank.eco.data.local.util.TestDataLoader
import codeasus.projects.bank.eco.domain.local.repository.customer.CustomerRepository
import codeasus.projects.bank.eco.domain.local.repository.system_message.SystemMessageRepository
import codeasus.projects.bank.eco.domain.local.repository.transaction.TransactionRepository
import codeasus.projects.bank.eco.domain.local.repository.user.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideUserRepository(@ApplicationContext context: Context): UserRepository {
        return UserRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideTestData(
        userRepository: UserRepository,
        customerRepository: CustomerRepository,
        transactionRepository: TransactionRepository,
        systemMessageRepository: SystemMessageRepository
    ): TestDataLoader {
        return TestDataLoader(
            userRepository,
            customerRepository,
            transactionRepository,
            systemMessageRepository
        )
    }
}