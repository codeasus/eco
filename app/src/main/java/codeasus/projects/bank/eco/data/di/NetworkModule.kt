package codeasus.projects.bank.eco.data.di

import codeasus.projects.bank.eco.data.remote.repository.MockPaymentRepository
import codeasus.projects.bank.eco.domain.remote.repository.PaymentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providePaymentRepository(): PaymentRepository {
        return MockPaymentRepository()
    }
}