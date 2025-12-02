package codeasus.projects.bank.eco.di

import codeasus.projects.bank.eco.data.remote.network.getHttpClient
import codeasus.projects.bank.eco.data.remote.repository.BankingRemoteRepository
import codeasus.projects.bank.eco.domain.remote.repository.banking.BankingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return getHttpClient()
    }

    @Provides
    @Singleton
    fun provideBankAccountRepository(httpClient: HttpClient): BankingRepository {
        return BankingRemoteRepository(httpClient)
    }
}