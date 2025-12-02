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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
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
    val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    return TransactionEntity(
        id = this.id.toString(),
        internalAccountNumber = this.internalAccountNumber,
        externalAccountNumber = this.externalAccountNumber,
        amount = this.amount,
        currency = this.currency.name,
        rate = this.rate,
        type = this.type.name,
        status = this.status.name,
        createdAt = this.createdAt.format(formatter),
        updatedAt = this.updatedAt.format(formatter)
    )
}

fun TransactionEntity.toTransactionModel(): TransactionModel {
    val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    return TransactionModel(
        id = UUID.fromString(this.id),
        externalAccountNumber = this.externalAccountNumber,
        internalAccountNumber = this.internalAccountNumber,
        amount = this.amount,
        currency = Currency.valueOf(this.currency),
        rate = this.rate,
        type = TransactionType.valueOf(this.type),
        status = TransactionStatus.valueOf(this.status),
        createdAt = LocalDateTime.parse(this.createdAt, formatter),
        updatedAt = LocalDateTime.parse(this.updatedAt, formatter)
    )
}

// SystemMessage Mappers
fun SystemMessageModel.toSystemMessageEntity(): SystemMessageEntity {
    val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    return SystemMessageEntity(
        title = this.title,
        content = this.content,
        priority = this.priority.value,
        createdAt = this.createdAt.format(formatter)
    )
}

fun SystemMessageEntity.toSystemMessageModel(): SystemMessageModel {
    val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    return SystemMessageModel(
        title = this.title,
        content = this.content,
        priority = Priority.fromInt(this.priority),
        createdAt = LocalDateTime.parse(this.createdAt, formatter)
    )
}

//BankAccount Mappers
fun UserBankAccountModel.toBankAccountEntity(): BankAccountEntity {
    val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    return BankAccountEntity(
        name = this.name,
        number = this.number,
        type = this.type.name,
        scheme = this.scheme.name,
        balance = this.balance,
        cvv = this.cvv,
        expiryDate = this.expiryDate.format(formatter)
    )
}

fun BankAccountEntity.toUserBankAccountModel(): UserBankAccountModel {
    val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    return UserBankAccountModel(
        id = this.id,
        name = this.name,
        number = this.number,
        type = BankAccountType.valueOf(this.type),
        scheme = BankAccountScheme.valueOf(this.scheme),
        balance = this.balance,
        cvv = this.cvv,
        expiryDate = LocalDateTime.parse(this.expiryDate, formatter)
    )
}