package codeasus.projects.bank.eco.data.local.mappers

import codeasus.projects.bank.eco.data.local.entity.BankAccountEntity
import codeasus.projects.bank.eco.data.local.entity.CustomerEntity
import codeasus.projects.bank.eco.data.local.entity.SystemMessageEntity
import codeasus.projects.bank.eco.data.local.entity.TransactionEntity
import codeasus.projects.bank.eco.domain.local.model.customer.CustomerBankAccountModel
import codeasus.projects.bank.eco.domain.local.model.customer.CustomerModel
import codeasus.projects.bank.eco.domain.local.model.enums.BankAccountScheme
import codeasus.projects.bank.eco.domain.local.model.enums.BankAccountType
import codeasus.projects.bank.eco.domain.local.model.enums.Currency
import codeasus.projects.bank.eco.domain.local.model.enums.Priority
import codeasus.projects.bank.eco.domain.local.model.enums.TransactionStatus
import codeasus.projects.bank.eco.domain.local.model.enums.TransactionType
import codeasus.projects.bank.eco.domain.local.model.system_message.SystemMessageModel
import codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel
import codeasus.projects.bank.eco.domain.local.model.user.UserBankAccountModel
import java.util.UUID

// Customer Mappers
fun CustomerModel.toCustomerEntity(): CustomerEntity {
    return CustomerEntity(
        name = this.name,
        profileImgResourceId = this.profileImgResId,
        bankAccountName = this.bankAccount.name,
        bankAccountNumber = this.bankAccount.number
    )
}

fun CustomerEntity.toCustomerModel(): CustomerModel {
    return CustomerModel(
        name = this.name,
        profileImgResId = this.profileImgResourceId,
        bankAccount = CustomerBankAccountModel(
            name = this.name,
            number = this.bankAccountNumber
        )
    )
}

// Transaction Mappers
fun TransactionModel.toTransactionEntity(): TransactionEntity {
    return TransactionEntity(
        id = this.id.toString(),
        internalAccountNumber = this.internalAccountNumber,
        externalAccountNumber = this.externalAccountNumber,
        amount = this.amount,
        currency = this.currency.name,
        rate = this.rate,
        type = this.type.name,
        status = this.status.name,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}

fun TransactionEntity.toTransactionModel(): TransactionModel {
    return TransactionModel(
        id = UUID.fromString(this.id),
        externalAccountNumber = this.externalAccountNumber,
        internalAccountNumber = this.internalAccountNumber,
        amount = this.amount,
        currency = Currency.valueOf(this.currency),
        rate = this.rate,
        type = TransactionType.valueOf(this.type),
        status = TransactionStatus.valueOf(this.status),
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
    )
}

// SystemMessage Mappers
fun SystemMessageModel.toSystemMessageEntity(): SystemMessageEntity {
    return SystemMessageEntity(
        title = this.title,
        content = this.content,
        priority = this.priority.value,
        createdAt = this.createdAt
    )
}

fun SystemMessageEntity.toSystemMessageModel(): SystemMessageModel {
    return SystemMessageModel(
        title = this.title,
        content = this.content,
        priority = Priority.fromInt(this.priority),
        createdAt = this.createdAt
    )
}

//BankAccount Mappers
fun UserBankAccountModel.toBankAccountEntity(): BankAccountEntity {
    return BankAccountEntity(
        name = this.name,
        number = this.number,
        type = this.type.name,
        scheme = this.scheme.name,
        balance = this.balance,
        currency = Currency.toCode(this.currency),
        cvv = this.cvv,
        expiryDate = this.expiryDate
    )
}

fun BankAccountEntity.toUserBankAccountModel(): UserBankAccountModel {
    return UserBankAccountModel(
        id = this.id,
        name = this.name,
        number = this.number,
        type = BankAccountType.valueOf(this.type),
        scheme = BankAccountScheme.valueOf(this.scheme),
        balance = this.balance,
        currency = Currency.fromCode(this.currency),
        cvv = this.cvv,
        expiryDate = this.expiryDate
    )
}