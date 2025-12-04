package codeasus.projects.bank.eco.data.local.util

import codeasus.projects.bank.eco.R
import codeasus.projects.bank.eco.domain.local.model.customer.CustomerBankAccountModel
import codeasus.projects.bank.eco.domain.local.model.customer.CustomerModel
import codeasus.projects.bank.eco.domain.local.model.enums.BankAccountType
import codeasus.projects.bank.eco.domain.local.model.enums.Currency
import codeasus.projects.bank.eco.domain.local.model.enums.Priority
import codeasus.projects.bank.eco.domain.local.model.enums.BankAccountScheme
import codeasus.projects.bank.eco.domain.local.model.enums.TransactionStatus
import codeasus.projects.bank.eco.domain.local.model.enums.TransactionType
import codeasus.projects.bank.eco.domain.local.model.system_message.SystemMessageModel
import codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel
import codeasus.projects.bank.eco.domain.local.model.user.UserBankAccountModel
import codeasus.projects.bank.eco.domain.local.model.user.UserModel
import codeasus.projects.bank.eco.domain.local.repository.customer.CustomerRepository
import codeasus.projects.bank.eco.domain.local.repository.system_message.SystemMessageRepository
import codeasus.projects.bank.eco.domain.local.repository.transaction.TransactionRepository
import codeasus.projects.bank.eco.domain.local.repository.user.BankAccountRepository
import codeasus.projects.bank.eco.domain.local.repository.user.UserRepository
import java.time.LocalDateTime
import javax.inject.Inject

class TestDataLoader @Inject constructor(
    private val userRepository: UserRepository,
    private val customerRepository: CustomerRepository,
    private val transactionRepository: TransactionRepository,
    private val systemMessageRepository: SystemMessageRepository,
    private val bankAccountRepository: BankAccountRepository
) {
    private fun getCustomers(): List<CustomerModel> {
        return listOf(
            CustomerModel(
                name = "Fiver",
                profileImgResId = R.drawable.fiver,
                bankAccount =
                    CustomerBankAccountModel(
                        name = "Fiver",
                        number = "1234567800000001"
                    )
            ),
            CustomerModel(
                name = "Bruce Wayne",
                profileImgResId = R.drawable.bruce_wayne,
                bankAccount =
                    CustomerBankAccountModel(
                        name = "Bruce Wayne",
                        number = "1234567800000002"
                    )
            ),
            CustomerModel(
                name = "Danny Fernandez",
                profileImgResId = R.drawable.danny_fernandez,
                bankAccount =
                    CustomerBankAccountModel(
                        name = "Danny Fernandez",
                        number = "1234567800000003"
                    )
            ),
            CustomerModel(
                name = "Tinder",
                profileImgResId = R.drawable.tinder,
                bankAccount =
                    CustomerBankAccountModel(
                        name = "Tinder",
                        number = "1234567800000004"
                    )
            ),
            CustomerModel(
                name = "Bank of America Ltd.",
                profileImgResId = R.drawable.bank_of_america,
                bankAccount =
                    CustomerBankAccountModel(
                        name = "Bank of America Ltd.",
                        number = "1234567800000005"
                    )
            ),
            CustomerModel(
                name = "Wolt",
                profileImgResId = R.drawable.wolt,
                bankAccount =
                    CustomerBankAccountModel(
                        name = "Wolt",
                        number = "1234567800000006"
                    )
            ),
            CustomerModel(
                name = "Kelly Robinson",
                profileImgResId = R.drawable.kelly_robinson,
                bankAccount =
                    CustomerBankAccountModel(
                        name = "Kelly Robinson",
                        number = "1234567800000007"
                    )
            )
        )
    }

    private val userBankAccountOne = UserBankAccountModel(
        id = 0,
        name = "Albert Flores",
        number = "1234567890020003",
        balance = 123.99,
        currency = Currency.USD,
        scheme = BankAccountScheme.VISA,
        type = BankAccountType.NORMAL,
        cvv = "123",
        expiryDate = LocalDateTime.now().plusYears(3)
    )
    private val userBankAccountTwo = UserBankAccountModel(
        id = 1,
        name = "Albert Flores",
        number = "9876000000003245",
        scheme = BankAccountScheme.MASTERCARD,
        type = BankAccountType.PLATINUM,
        balance = 4503.25,
        currency = Currency.PLN,
        cvv = "456",
        expiryDate = LocalDateTime.now().plusYears(2).plusMonths(4)
    )

    private val appUser = UserModel(
        name = "Albert Flores",
        tagName = "albert_flores",
        profileImageResId = R.drawable.albert_flores,
    )

    suspend fun load() {
        addAppUser()
        addBankAccounts()
        addCustomers()
        saveTransactions()
        saveSystemMessages()
    }

    private suspend fun addAppUser() {
        userRepository.saveUser(appUser)
    }

    private suspend fun addBankAccounts() {
        bankAccountRepository.deleteBankAccounts()
        bankAccountRepository.insertBankAccount(userBankAccountOne)
        bankAccountRepository.insertBankAccount(userBankAccountTwo)
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
                internalAccountNumber = userBankAccountTwo.number,
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
                internalAccountNumber = userBankAccountOne.number,
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
                internalAccountNumber = userBankAccountOne.number,
                amount = 5.2,
                currency = Currency.PLN,
                rate = 0.5,
                type = TransactionType.WITHDRAWAL,
                status = TransactionStatus.FAILED,
                createdAt = LocalDateTime.now().minusYears(1),
                updatedAt = LocalDateTime.now().minusDays(9),
            ),
            TransactionModel(
                externalAccountNumber = customers[3].bankAccount.number,
                internalAccountNumber = userBankAccountTwo.number,
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
                internalAccountNumber = userBankAccountTwo.number,
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
                internalAccountNumber = userBankAccountOne.number,
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
                internalAccountNumber = userBankAccountTwo.number,
                amount = 400.0,
                currency = Currency.PLN,
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

    suspend fun saveSystemMessages() {
        val titles = listOf(
            "Security Update",
            "System Maintenance",
            "New Feature Available",
            "Account Verification",
            "Payment Processing",
            "Database Backup",
            "Service Alert",
            "Performance Optimization",
            "User Activity Report",
            "Network Configuration",
            "Transaction Limit Update",
            "Mobile App Update",
            "Card Activation",
            "Interest Rate Change",
            "Privacy Policy Update"
        )

        val contents = listOf(
            "Your account security has been enhanced with two-factor authentication.",
            "Scheduled maintenance will occur tonight from 2:00 AM to 4:00 AM.",
            "Mobile banking app has been updated with improved user interface.",
            "Please verify your account information to continue using our services.",
            "Payment processing system has been upgraded for faster transactions.",
            "Daily database backup completed successfully at 3:30 AM.",
            "Temporary service interruption expected during peak hours.",
            "System performance has been optimized for better response times.",
            "Monthly user activity report is now available in your dashboard.",
            "Network configuration updated to improve connection stability.",
            "Daily transaction limits have been increased for premium accounts.",
            "New version of mobile app is available with enhanced security features.",
            "Your new debit card has been activated and is ready for use.",
            "Interest rates for savings accounts have been updated effective today.",
            "Updated privacy policy is now available. Please review the changes."
        )

        val priorities = Priority.entries.toTypedArray()
        systemMessageRepository.deleteSystemMessages()
        repeat(15) { index ->
            val systemMessage = SystemMessageModel(
                title = titles[index],
                content = contents[index],
                priority = priorities.random(),
                createdAt = LocalDateTime.now().minusHours((0..168).random().toLong())
            )
            systemMessageRepository.insertSystemMessage(systemMessage)
        }
    }
}