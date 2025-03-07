package codeasus.projects.bank.eco.core.ui.shared.view.utils

import codeasus.projects.bank.eco.R
import codeasus.projects.bank.eco.domain.local.model.customer.CustomerModel
import codeasus.projects.bank.eco.domain.local.model.enums.Currency
import java.time.LocalDateTime

object DataSourceDefaults {

    val unknownUser = codeasus.projects.bank.eco.domain.local.model.user.UserModel(
        name = "Unknown",
        profileImageResId = R.drawable.unknown,
        bankAccounts = listOf(
            codeasus.projects.bank.eco.domain.local.model.user.UserBankAccountModel(
                name = "Unknown",
                number = "0000 0000 0000 0000",
                balance = 0.0,
                ccv = "123",
                expiryDate = LocalDateTime.now()
            )
        )
    )

    fun getCustomers(): List<CustomerModel> {
        return listOf(
            CustomerModel(
                name = "Fiver",
                profileImgResId = R.drawable.fiver,
                bankAccount =
                codeasus.projects.bank.eco.domain.local.model.customer.CustomerBankAccountModel(
                    name = "Fiver",
                    number = "1234 5678 0000 9099"
                )
            ),
            CustomerModel(
                name = "Bruce Wayne",
                profileImgResId = R.drawable.bruce_wayne,
                bankAccount =
                codeasus.projects.bank.eco.domain.local.model.customer.CustomerBankAccountModel(
                    name = "Bruce Wayne",
                    number = "1234 5678 0000 9099"
                )
            ),
            CustomerModel(
                name = "Danny Fernandez",
                profileImgResId = R.drawable.danny_fernandez,
                bankAccount =
                codeasus.projects.bank.eco.domain.local.model.customer.CustomerBankAccountModel(
                    name = "Danny Fernandez",
                    number = "1234 5678 0000 9099"
                )
            ),
            CustomerModel(
                name = "Tinder",
                profileImgResId = R.drawable.tinder,
                bankAccount =
                codeasus.projects.bank.eco.domain.local.model.customer.CustomerBankAccountModel(
                    name = "Tinder",
                    number = "1234 5678 0000 9099"
                )

            ),
            CustomerModel(
                name = "Bank of America Ltd.",
                profileImgResId = R.drawable.bank_of_america,
                bankAccount =
                codeasus.projects.bank.eco.domain.local.model.customer.CustomerBankAccountModel(
                    name = "Bank of America Ltd.",
                    number = "1234 5678 0000 9099"
                )
            ),
            CustomerModel(
                name = "Wolt",
                profileImgResId = R.drawable.wolt,
                bankAccount =
                codeasus.projects.bank.eco.domain.local.model.customer.CustomerBankAccountModel(
                    name = "Wolt",
                    number = "1234 5678 0000 9099"
                )
            ),
            CustomerModel(
                name = "Kelly Robinson",
                profileImgResId = R.drawable.kelly_robinson,
                bankAccount =
                codeasus.projects.bank.eco.domain.local.model.customer.CustomerBankAccountModel(
                    name = "Kelly Robinson",
                    number = "1234 5678 0000 9099"
                )
            )
        )
    }

    fun getTransactions(): List<codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel> {
        val customers = getCustomers()
        return listOf(
            codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel(
                externalAccountNumber = unknownUser.bankAccounts[0].number,
                internalAccountNumber = customers[0].bankAccount.number,
                amount = 45.23,
                currency = Currency.USD,
                rate = 2.4,
                type = codeasus.projects.bank.eco.domain.local.model.enums.TransactionType.TRANSFER,
                status = codeasus.projects.bank.eco.domain.local.model.enums.TransactionStatus.COMPLETED,
                createdAt = LocalDateTime.now().minusDays(10),
                updatedAt = LocalDateTime.now().minusDays(9)
            ),
            codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel(
                externalAccountNumber = unknownUser.bankAccounts[0].number,
                internalAccountNumber = customers[1].bankAccount.number,
                amount = 28.0,
                currency = Currency.EUR,
                rate = 2.0,
                type = codeasus.projects.bank.eco.domain.local.model.enums.TransactionType.WITHDRAWAL,
                status = codeasus.projects.bank.eco.domain.local.model.enums.TransactionStatus.COMPLETED,
                createdAt = LocalDateTime.now().minusDays(10),
                updatedAt = LocalDateTime.now().minusDays(9)
            ),
            codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel(
                externalAccountNumber = unknownUser.bankAccounts[0].number,
                internalAccountNumber = customers[2].bankAccount.number,
                amount = 5.2,
                currency = Currency.AZN,
                rate = 0.5,
                type = codeasus.projects.bank.eco.domain.local.model.enums.TransactionType.WITHDRAWAL,
                status = codeasus.projects.bank.eco.domain.local.model.enums.TransactionStatus.FAILED,
                createdAt = LocalDateTime.now().minusYears(1),
                updatedAt = LocalDateTime.now().minusDays(9)
            ),
            codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel(
                externalAccountNumber = unknownUser.bankAccounts[0].number,
                internalAccountNumber = customers[3].bankAccount.number,
                amount = 0.45,
                currency = Currency.USD,
                rate = 4.0,
                type = codeasus.projects.bank.eco.domain.local.model.enums.TransactionType.REFUND,
                status = codeasus.projects.bank.eco.domain.local.model.enums.TransactionStatus.CANCELED,
                createdAt = LocalDateTime.now().minusDays(5),
                updatedAt = LocalDateTime.now().minusDays(4)
            ),
            codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel(
                externalAccountNumber = unknownUser.bankAccounts[0].number,
                internalAccountNumber = customers[4].bankAccount.number,
                amount = 100.99,
                currency = Currency.EUR,
                rate = 2.5,
                type = codeasus.projects.bank.eco.domain.local.model.enums.TransactionType.TRANSFER,
                status = codeasus.projects.bank.eco.domain.local.model.enums.TransactionStatus.PENDING,
                createdAt = LocalDateTime.now().minusDays(90),
                updatedAt = LocalDateTime.now().minusDays(87)
            ),
            codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel(
                externalAccountNumber = unknownUser.bankAccounts[0].number,
                internalAccountNumber = customers[5].bankAccount.number,
                amount = 1000.0,
                currency = Currency.USD,
                rate = 10.0,
                type = codeasus.projects.bank.eco.domain.local.model.enums.TransactionType.TRANSFER,
                status = codeasus.projects.bank.eco.domain.local.model.enums.TransactionStatus.COMPLETED,
                createdAt = LocalDateTime.now().minusDays(150),
                updatedAt = LocalDateTime.now().minusDays(148)
            ),
            codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel(
                externalAccountNumber = unknownUser.bankAccounts[0].number,
                internalAccountNumber = customers[6].bankAccount.number,
                amount = 400.0,
                currency = Currency.AZN,
                rate = 1.4,
                type = codeasus.projects.bank.eco.domain.local.model.enums.TransactionType.DEPOSIT,
                status = codeasus.projects.bank.eco.domain.local.model.enums.TransactionStatus.COMPLETED,
                createdAt = LocalDateTime.now().minusDays(200),
                updatedAt = LocalDateTime.now().minusDays(201)
            )
        )
    }
}