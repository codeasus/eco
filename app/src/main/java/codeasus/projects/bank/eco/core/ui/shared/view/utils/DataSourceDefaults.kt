package codeasus.projects.bank.eco.core.ui.shared.view.utils

import codeasus.projects.bank.eco.R
import codeasus.projects.bank.eco.domain.local.model.customer.CustomerBankAccountModel
import codeasus.projects.bank.eco.domain.local.model.customer.CustomerModel
import codeasus.projects.bank.eco.domain.local.model.enums.BankAccountType
import codeasus.projects.bank.eco.domain.local.model.enums.Currency
import codeasus.projects.bank.eco.domain.local.model.enums.TransactionStatus
import codeasus.projects.bank.eco.domain.local.model.enums.TransactionType
import codeasus.projects.bank.eco.domain.local.model.user.UserBankAccountModel
import codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel
import codeasus.projects.bank.eco.domain.local.model.user.UserModel
import java.time.LocalDateTime

object DataSourceDefaults {

    val unknownUser = UserModel(
        name = "Unknown",
        profileImageResId = R.drawable.unknown,
        bankAccounts = listOf(
            UserBankAccountModel(
                id = "card_unknown_normal_1",
                name = "Unknown",
                number = "0000 0000 0000 0000",
                type = BankAccountType.NORMAL,
                balance = 0.0,
                ccv = "123",
                expiryDate = LocalDateTime.now()
            ),
            UserBankAccountModel(
                id = "card_unknown_platinum_1",
                name = "Unknown",
                number = "0000 0000 0000 0000",
                type = BankAccountType.PLATINUM,
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
                CustomerBankAccountModel(
                    name = "Fiver",
                    number = "1234 5678 0000 9099"
                )
            ),
            CustomerModel(
                name = "Bruce Wayne",
                profileImgResId = R.drawable.bruce_wayne,
                bankAccount =
                CustomerBankAccountModel(
                    name = "Bruce Wayne",
                    number = "1234 5678 0000 9099"
                )
            ),
            CustomerModel(
                name = "Danny Fernandez",
                profileImgResId = R.drawable.danny_fernandez,
                bankAccount =
                CustomerBankAccountModel(
                    name = "Danny Fernandez",
                    number = "1234 5678 0000 9099"
                )
            ),
            CustomerModel(
                name = "Tinder",
                profileImgResId = R.drawable.tinder,
                bankAccount =
                CustomerBankAccountModel(
                    name = "Tinder",
                    number = "1234 5678 0000 9099"
                )

            ),
            CustomerModel(
                name = "Bank of America Ltd.",
                profileImgResId = R.drawable.bank_of_america,
                bankAccount =
                CustomerBankAccountModel(
                    name = "Bank of America Ltd.",
                    number = "1234 5678 0000 9099"
                )
            ),
            CustomerModel(
                name = "Wolt",
                profileImgResId = R.drawable.wolt,
                bankAccount =
                CustomerBankAccountModel(
                    name = "Wolt",
                    number = "1234 5678 0000 9099"
                )
            ),
            CustomerModel(
                name = "Kelly Robinson",
                profileImgResId = R.drawable.kelly_robinson,
                bankAccount =
                CustomerBankAccountModel(
                    name = "Kelly Robinson",
                    number = "1234 5678 0000 9099"
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
                currency = Currency.AZN,
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
                currency = Currency.AZN,
                rate = 1.4,
                type = TransactionType.DEPOSIT,
                status = TransactionStatus.COMPLETED,
                createdAt = LocalDateTime.now().minusDays(200),
                updatedAt = LocalDateTime.now().minusDays(201)
            )
        )
    }
}