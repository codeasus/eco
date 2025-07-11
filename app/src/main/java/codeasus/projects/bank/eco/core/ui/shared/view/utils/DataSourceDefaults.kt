package codeasus.projects.bank.eco.core.ui.shared.view.utils

import codeasus.projects.bank.eco.R
import codeasus.projects.bank.eco.domain.local.model.customer.CustomerBankAccountModel
import codeasus.projects.bank.eco.domain.local.model.customer.CustomerModel
import codeasus.projects.bank.eco.domain.local.model.enums.BankAccountType
import codeasus.projects.bank.eco.domain.local.model.enums.Currency
import codeasus.projects.bank.eco.domain.local.model.enums.Priority
import codeasus.projects.bank.eco.domain.local.model.enums.TransactionStatus
import codeasus.projects.bank.eco.domain.local.model.enums.TransactionType
import codeasus.projects.bank.eco.domain.local.model.system_message.SystemMessageModel
import codeasus.projects.bank.eco.domain.local.model.user.UserBankAccountModel
import codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel
import codeasus.projects.bank.eco.domain.local.model.user.UserModel
import java.time.LocalDateTime

object DataSourceDefaults {

    val exampleUser = UserModel(
        name = "Henry Cavill",
        profileImageResId = R.drawable.henry_cavill,
        tagName = "henry_cavill",
        bankAccounts = listOf(
            UserBankAccountModel(
                id = "card_unknown_normal_1",
                name = "Henry Cavill",
                number = "0000000000000345",
                type = BankAccountType.NORMAL,
                balance = 0.0,
                cvv = "123",
                expiryDate = LocalDateTime.now()
            ),
            UserBankAccountModel(
                id = "card_unknown_platinum_1",
                name = "Henry Cavill",
                number = "0000000000008923",
                type = BankAccountType.PLATINUM,
                balance = 0.0,
                cvv = "123",
                expiryDate = LocalDateTime.now()
            )
        )
    )

    val unknownUser = UserModel(
        name = "Unknown",
        profileImageResId = R.drawable.unknown,
        tagName = "unknown",
        bankAccounts = listOf(
            UserBankAccountModel(
                id = "card_unknown_normal_1",
                name = "Unknown",
                number = "0000000000000000",
                type = BankAccountType.NORMAL,
                balance = 0.0,
                cvv = "123",
                expiryDate = LocalDateTime.now()
            ),
            UserBankAccountModel(
                id = "card_unknown_platinum_1",
                name = "Unknown",
                number = "0000000000000000",
                type = BankAccountType.PLATINUM,
                balance = 0.0,
                cvv = "123",
                expiryDate = LocalDateTime.now()
            )
        )
    )

    fun getSystemMessages(): List<SystemMessageModel> {
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
            "Network Configuration"
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
            "Network configuration updated to improve connection stability."
        )

        val priorities = Priority.entries.toTypedArray()

        return (0..9).map { index ->
            SystemMessageModel(
                title = titles[index],
                content = contents[index],
                priority = priorities.random(),
                createdAt = LocalDateTime.now().minusHours((0..72).random().toLong())
            )
        }
    }

    fun getCustomerTransactions(): List<Pair<CustomerModel, TransactionModel>> {
        val customers = getCustomers()
        val transactions = getTransactions()

        return transactions.mapIndexed { index, transaction ->
            val customer = customers[index % customers.size]
            customer to transaction
        }
    }

    fun getCustomers(): List<CustomerModel> {
        return listOf(
            CustomerModel(
                name = "Fiver",
                profileImgResId = R.drawable.fiver,
                bankAccount =
                CustomerBankAccountModel(
                    name = "Fiver",
                    number = "1234567800009099"
                )
            ),
            CustomerModel(
                name = "Bruce Wayne",
                profileImgResId = R.drawable.bruce_wayne,
                bankAccount =
                CustomerBankAccountModel(
                    name = "Bruce Wayne",
                    number = "1234567800009099"
                )
            ),
            CustomerModel(
                name = "Danny Fernandez",
                profileImgResId = R.drawable.danny_fernandez,
                bankAccount =
                CustomerBankAccountModel(
                    name = "Danny Fernandez",
                    number = "1234567800009099"
                )
            ),
            CustomerModel(
                name = "Tinder",
                profileImgResId = R.drawable.tinder,
                bankAccount =
                CustomerBankAccountModel(
                    name = "Tinder",
                    number = "1234567800009099"
                )

            ),
            CustomerModel(
                name = "Bank of America Ltd.",
                profileImgResId = R.drawable.bank_of_america,
                bankAccount =
                CustomerBankAccountModel(
                    name = "Bank of America Ltd.",
                    number = "1234567800009099"
                )
            ),
            CustomerModel(
                name = "Wolt",
                profileImgResId = R.drawable.wolt,
                bankAccount =
                CustomerBankAccountModel(
                    name = "Wolt",
                    number = "1234567800009099"
                )
            ),
            CustomerModel(
                name = "Kelly Robinson",
                profileImgResId = R.drawable.kelly_robinson,
                bankAccount =
                CustomerBankAccountModel(
                    name = "Kelly Robinson",
                    number = "1234567800009099"
                )
            )
        )
    }

    fun getTransactions(): List<TransactionModel> {
        val customers = getCustomers()
        return listOf(
            TransactionModel(
                externalAccountNumber = unknownUser.bankAccounts[0].number,
                internalAccountNumber = customers[0].bankAccount.number,
                amount = 45.23,
                currency = Currency.USD,
                rate = 2.4,
                type = TransactionType.TRANSFER,
                status = TransactionStatus.COMPLETED,
                createdAt = LocalDateTime.now().minusDays(10),
                updatedAt = LocalDateTime.now().minusDays(9)
            ),
            TransactionModel(
                externalAccountNumber = unknownUser.bankAccounts[0].number,
                internalAccountNumber = customers[1].bankAccount.number,
                amount = 28.0,
                currency = Currency.EUR,
                rate = 2.0,
                type = TransactionType.WITHDRAWAL,
                status = TransactionStatus.COMPLETED,
                createdAt = LocalDateTime.now().minusDays(10),
                updatedAt = LocalDateTime.now().minusDays(9)
            ),
            TransactionModel(
                externalAccountNumber = unknownUser.bankAccounts[0].number,
                internalAccountNumber = customers[2].bankAccount.number,
                amount = 5.2,
                currency = Currency.PLN,
                rate = 0.5,
                type = TransactionType.WITHDRAWAL,
                status = TransactionStatus.FAILED,
                createdAt = LocalDateTime.now().minusYears(1),
                updatedAt = LocalDateTime.now().minusDays(9)
            ),
            TransactionModel(
                externalAccountNumber = unknownUser.bankAccounts[0].number,
                internalAccountNumber = customers[3].bankAccount.number,
                amount = 0.45,
                currency = Currency.USD,
                rate = 4.0,
                type = TransactionType.REFUND,
                status = TransactionStatus.CANCELED,
                createdAt = LocalDateTime.now().minusDays(5),
                updatedAt = LocalDateTime.now().minusDays(4)
            ),
            TransactionModel(
                externalAccountNumber = unknownUser.bankAccounts[0].number,
                internalAccountNumber = customers[4].bankAccount.number,
                amount = 100.99,
                currency = Currency.EUR,
                rate = 2.5,
                type = TransactionType.TRANSFER,
                status = TransactionStatus.PENDING,
                createdAt = LocalDateTime.now().minusDays(90),
                updatedAt = LocalDateTime.now().minusDays(87)
            ),
            TransactionModel(
                externalAccountNumber = unknownUser.bankAccounts[0].number,
                internalAccountNumber = customers[5].bankAccount.number,
                amount = 1000.0,
                currency = Currency.USD,
                rate = 10.0,
                type = TransactionType.TRANSFER,
                status = TransactionStatus.COMPLETED,
                createdAt = LocalDateTime.now().minusDays(150),
                updatedAt = LocalDateTime.now().minusDays(148)
            ),
            TransactionModel(
                externalAccountNumber = unknownUser.bankAccounts[0].number,
                internalAccountNumber = customers[6].bankAccount.number,
                amount = 400.0,
                currency = Currency.PLN,
                rate = 1.4,
                type = TransactionType.DEPOSIT,
                status = TransactionStatus.COMPLETED,
                createdAt = LocalDateTime.now().minusDays(200),
                updatedAt = LocalDateTime.now().minusDays(201)
            )
        )
    }
}