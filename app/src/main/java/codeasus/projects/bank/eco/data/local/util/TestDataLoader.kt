package codeasus.projects.bank.eco.data.local.util

import codeasus.projects.bank.eco.R
import codeasus.projects.bank.eco.domain.local.model.customer.CustomerBankAccountModel
import codeasus.projects.bank.eco.domain.local.model.customer.CustomerModel
import codeasus.projects.bank.eco.domain.local.model.enums.Currency
import codeasus.projects.bank.eco.domain.local.repository.customer.CustomerRepository
import codeasus.projects.bank.eco.domain.local.repository.transaction.TransactionRepository
import codeasus.projects.bank.eco.domain.local.repository.user.UserRepository
import java.time.LocalDateTime
import javax.inject.Inject

class TestDataLoader @Inject constructor(
    val userRepository: UserRepository,
    val customerRepository: CustomerRepository,
    val transactionRepository: TransactionRepository
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

    private val appUser = codeasus.projects.bank.eco.domain.local.model.user.UserModel(
        name = "Albert Flores",
        profileImageResId = R.drawable.albert_flores,
        bankAccounts = listOf(
            codeasus.projects.bank.eco.domain.local.model.user.UserBankAccountModel(
                name = "Albert Flores",
                number = "1234 5678 9002 0003",
                balance = 123.99,
                ccv = "123",
                expiryDate = LocalDateTime.now().plusYears(3)
            ),
            codeasus.projects.bank.eco.domain.local.model.user.UserBankAccountModel(
                name = "Albert Flores",
                number = "9876 0000 0000 3245",
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
        for (customer in customers) {
            customerRepository.saveCustomer(customer)
        }
    }

    private suspend fun saveTransactions() {
        val customers = getCustomers()

        val transactions = listOf(
            codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel(
                fromAccountNumber = appUser.bankAccounts[0].number,
                toAccountNumber = customers[0].bankAccount.number,
                amount = 45.23,
                currency = Currency.USD,
                rate = 2.4,
                type = codeasus.projects.bank.eco.domain.local.model.enums.TransactionType.TRANSFER,
                status = codeasus.projects.bank.eco.domain.local.model.enums.TransactionStatus.COMPLETED,
                createdAt = LocalDateTime.now().minusDays(10),
                updatedAt = LocalDateTime.now().minusDays(9)
            ),
            codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel(
                fromAccountNumber = appUser.bankAccounts[0].number,
                toAccountNumber = customers[1].bankAccount.number,
                amount = 28.0,
                currency = Currency.EUR,
                rate = 2.0,
                type = codeasus.projects.bank.eco.domain.local.model.enums.TransactionType.WITHDRAWAL,
                status = codeasus.projects.bank.eco.domain.local.model.enums.TransactionStatus.COMPLETED,
                createdAt = LocalDateTime.now().minusDays(10),
                updatedAt = LocalDateTime.now().minusDays(9)
            ),
            codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel(
                fromAccountNumber = appUser.bankAccounts[0].number,
                toAccountNumber = customers[2].bankAccount.number,
                amount = 5.2,
                currency = Currency.AZN,
                rate = 0.5,
                type = codeasus.projects.bank.eco.domain.local.model.enums.TransactionType.WITHDRAWAL,
                status = codeasus.projects.bank.eco.domain.local.model.enums.TransactionStatus.FAILED,
                createdAt = LocalDateTime.now().minusYears(1),
                updatedAt = LocalDateTime.now().minusDays(9)
            ),
            codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel(
                fromAccountNumber = appUser.bankAccounts[0].number,
                toAccountNumber = customers[3].bankAccount.number,
                amount = 0.45,
                currency = Currency.USD,
                rate = 4.0,
                type = codeasus.projects.bank.eco.domain.local.model.enums.TransactionType.REFUND,
                status = codeasus.projects.bank.eco.domain.local.model.enums.TransactionStatus.CANCELED,
                createdAt = LocalDateTime.now().minusDays(5),
                updatedAt = LocalDateTime.now().minusDays(4)
            ),
            codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel(
                fromAccountNumber = appUser.bankAccounts[0].number,
                toAccountNumber = customers[4].bankAccount.number,
                amount = 100.99,
                currency = Currency.EUR,
                rate = 2.5,
                type = codeasus.projects.bank.eco.domain.local.model.enums.TransactionType.TRANSFER,
                status = codeasus.projects.bank.eco.domain.local.model.enums.TransactionStatus.PENDING,
                createdAt = LocalDateTime.now().minusDays(90),
                updatedAt = LocalDateTime.now().minusDays(87)
            ),
            codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel(
                fromAccountNumber = appUser.bankAccounts[0].number,
                toAccountNumber = customers[5].bankAccount.number,
                amount = 1000.0,
                currency = Currency.USD,
                rate = 10.0,
                type = codeasus.projects.bank.eco.domain.local.model.enums.TransactionType.TRANSFER,
                status = codeasus.projects.bank.eco.domain.local.model.enums.TransactionStatus.COMPLETED,
                createdAt = LocalDateTime.now().minusDays(150),
                updatedAt = LocalDateTime.now().minusDays(148)
            ),
            codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel(
                fromAccountNumber = appUser.bankAccounts[0].number,
                toAccountNumber = customers[6].bankAccount.number,
                amount = 400.0,
                currency = Currency.AZN,
                rate = 1.4,
                type = codeasus.projects.bank.eco.domain.local.model.enums.TransactionType.DEPOSIT,
                status = codeasus.projects.bank.eco.domain.local.model.enums.TransactionStatus.COMPLETED,
                createdAt = LocalDateTime.now().minusDays(200),
                updatedAt = LocalDateTime.now().minusDays(201)
            )
        )

        for (transaction in transactions) {
            transactionRepository.saveTransaction(transaction)
        }
    }
}