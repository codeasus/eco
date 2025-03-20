package codeasus.projects.bank.eco.data.local.util

import codeasus.projects.bank.eco.R
import codeasus.projects.bank.eco.domain.local.model.customer.CustomerBankAccountModel
import codeasus.projects.bank.eco.domain.local.model.customer.CustomerModel
import codeasus.projects.bank.eco.domain.local.model.enums.BankAccountType
import codeasus.projects.bank.eco.domain.local.model.enums.Currency
import codeasus.projects.bank.eco.domain.local.model.enums.TransactionStatus
import codeasus.projects.bank.eco.domain.local.model.enums.TransactionType
import codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel
import codeasus.projects.bank.eco.domain.local.model.user.UserBankAccountModel
import codeasus.projects.bank.eco.domain.local.model.user.UserModel
import codeasus.projects.bank.eco.domain.local.repository.customer.CustomerRepository
import codeasus.projects.bank.eco.domain.local.repository.transaction.TransactionRepository
import codeasus.projects.bank.eco.domain.local.repository.user.UserRepository
import java.time.LocalDateTime
import javax.inject.Inject

class TestDataLoader @Inject constructor(
    val userRepository: UserRepository,
    private val customerRepository: CustomerRepository,
    private val transactionRepository: TransactionRepository
) {

    private fun getCustomers(): List<CustomerModel> {
        return listOf(
            CustomerModel(
                name = "Fiver",
                profileImgResId = R.drawable.fiver,
                bankAccount =
                    CustomerBankAccountModel(
                        name = "Fiver",
                        number = "1234 5678 0000 0001"
                    )
            ),
            CustomerModel(
                name = "Bruce Wayne",
                profileImgResId = R.drawable.bruce_wayne,
                bankAccount =
                    CustomerBankAccountModel(
                        name = "Bruce Wayne",
                        number = "1234 5678 0000 0002"
                    )
            ),
            CustomerModel(
                name = "Danny Fernandez",
                profileImgResId = R.drawable.danny_fernandez,
                bankAccount =
                    CustomerBankAccountModel(
                        name = "Danny Fernandez",
                        number = "1234 5678 0000 0003"
                    )
            ),
            CustomerModel(
                name = "Tinder",
                profileImgResId = R.drawable.tinder,
                bankAccount =
                    CustomerBankAccountModel(
                        name = "Tinder",
                        number = "1234 5678 0000 0004"
                    )

            ),
            CustomerModel(
                name = "Bank of America Ltd.",
                profileImgResId = R.drawable.bank_of_america,
                bankAccount =
                    CustomerBankAccountModel(
                        name = "Bank of America Ltd.",
                        number = "1234 5678 0000 0005"
                    )
            ),
            CustomerModel(
                name = "Wolt",
                profileImgResId = R.drawable.wolt,
                bankAccount =
                    CustomerBankAccountModel(
                        name = "Wolt",
                        number = "1234 5678 0000 0006"
                    )
            ),
            CustomerModel(
                name = "Kelly Robinson",
                profileImgResId = R.drawable.kelly_robinson,
                bankAccount =
                    CustomerBankAccountModel(
                        name = "Kelly Robinson",
                        number = "1234 5678 0000 0007"
                    )
            )
        )
    }

    private val appUser = UserModel(
        name = "Albert Flores",
        profileImageResId = R.drawable.albert_flores,
        bankAccounts = listOf(
            UserBankAccountModel(
                name = "Albert Flores",
                number = "1234 5678 9002 0003",
                balance = 123.99,
                type = BankAccountType.NORMAL,
                ccv = "123",
                expiryDate = LocalDateTime.now().plusYears(3)
            ),
            UserBankAccountModel(
                name = "Albert Flores",
                number = "9876 0000 0000 3245",
                type = BankAccountType.PLATINUM,
                balance = 4503.25,
                ccv = "456",
                expiryDate = LocalDateTime.now().plusYears(2).plusMonths(4)
            )
        )
    )

    suspend fun load() {
        addAppUser()
        addCustomers()
        saveTransactions()
    }

    private suspend fun addAppUser() {
        userRepository.saveUser(appUser)
    }

    private suspend fun addCustomers() {
        val customers = getCustomers()
        customerRepository.deleteCustomers()
        customers.forEach { customerRepository.saveCustomer(it) }
    }

    private suspend fun saveTransactions() {
        val customers = getCustomers()

        val transactions = listOf(
            TransactionModel(
                externalAccountNumber = customers[0].bankAccount.number,
                internalAccountNumber = appUser.bankAccounts[1].number,
                amount = 45.23,
                currency = Currency.USD,
                rate = 2.4,
                type = TransactionType.TRANSFER,
                status = TransactionStatus.COMPLETED,
                createdAt = LocalDateTime.now().minusDays(10),
                updatedAt = LocalDateTime.now().minusDays(9),
            ),
            TransactionModel(
                externalAccountNumber = customers[1].bankAccount.number,
                internalAccountNumber = appUser.bankAccounts[0].number,
                amount = 28.0,
                currency = Currency.EUR,
                rate = 2.0,
                type = TransactionType.WITHDRAWAL,
                status = TransactionStatus.COMPLETED,
                createdAt = LocalDateTime.now().minusDays(10),
                updatedAt = LocalDateTime.now().minusDays(9),
            ),
            TransactionModel(
                externalAccountNumber = customers[2].bankAccount.number,
                internalAccountNumber = appUser.bankAccounts[1].number,
                amount = 5.2,
                currency = Currency.AZN,
                rate = 0.5,
                type = TransactionType.WITHDRAWAL,
                status = TransactionStatus.FAILED,
                createdAt = LocalDateTime.now().minusYears(1),
                updatedAt = LocalDateTime.now().minusDays(9),
            ),
            TransactionModel(
                externalAccountNumber = customers[3].bankAccount.number,
                internalAccountNumber = appUser.bankAccounts[1].number,
                amount = 0.45,
                currency = Currency.USD,
                rate = 4.0,
                type = TransactionType.REFUND,
                status = TransactionStatus.CANCELED,
                createdAt = LocalDateTime.now().minusDays(5),
                updatedAt = LocalDateTime.now().minusDays(4),
            ),
            TransactionModel(
                externalAccountNumber = customers[4].bankAccount.number,
                internalAccountNumber = appUser.bankAccounts[0].number,
                amount = 100.99,
                currency = Currency.EUR,
                rate = 2.5,
                type = TransactionType.TRANSFER,
                status = TransactionStatus.PENDING,
                createdAt = LocalDateTime.now().minusDays(90),
                updatedAt = LocalDateTime.now().minusDays(87),
            ),
            TransactionModel(
                externalAccountNumber = customers[5].bankAccount.number,
                internalAccountNumber = appUser.bankAccounts[0].number,
                amount = 1000.0,
                currency = Currency.USD,
                rate = 10.0,
                type = TransactionType.TRANSFER,
                status = TransactionStatus.COMPLETED,
                createdAt = LocalDateTime.now().minusDays(150),
                updatedAt = LocalDateTime.now().minusDays(148),
            ),
            TransactionModel(
                externalAccountNumber = customers[6].bankAccount.number,
                internalAccountNumber = appUser.bankAccounts[1].number,
                amount = 400.0,
                currency = Currency.AZN,
                rate = 1.4,
                type = TransactionType.DEPOSIT,
                status = TransactionStatus.COMPLETED,
                createdAt = LocalDateTime.now().minusDays(200),
                updatedAt = LocalDateTime.now().minusDays(201),
            )
        )
        transactionRepository.deleteTransactions()
        transactions.forEach { transactionRepository.saveTransaction(it) }
    }
}