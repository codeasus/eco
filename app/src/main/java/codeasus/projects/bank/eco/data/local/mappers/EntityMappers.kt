package codeasus.projects.bank.eco.data.local.mappers

import codeasus.projects.bank.eco.data.local.entity.CustomerEntity
import codeasus.projects.bank.eco.data.local.entity.TransactionEntity
import codeasus.projects.bank.eco.domain.local.model.customer.CustomerModel
import codeasus.projects.bank.eco.domain.local.model.enums.Currency
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
        bankAccount = codeasus.projects.bank.eco.domain.local.model.customer.CustomerBankAccountModel(
            name = this.name,
            number = this.bankAccountNumber
        )
    )
}

// Transaction Mappers
fun codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel.toTransactionEntity(): TransactionEntity {
    val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    return TransactionEntity(
        id = this.id.toString(),
        fromAccountNumber = this.fromAccountNumber,
        toAccountNumber = this.toAccountNumber,
        amount = this.amount,
        currency = this.currency.name,
        rate = this.rate,
        type = this.type.name,
        status = this.status.name,
        createdAt = this.createdAt.format(formatter),
        updatedAt = this.updatedAt.format(formatter)
    )
}

fun TransactionEntity.toTransactionModel(): codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel {
    val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    return codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel(
        id = UUID.fromString(this.id),
        fromAccountNumber = this.fromAccountNumber,
        toAccountNumber = this.toAccountNumber,
        amount = this.amount,
        currency = Currency.valueOf(this.currency),
        rate = this.rate,
        type = codeasus.projects.bank.eco.domain.local.model.enums.TransactionType.valueOf(this.type),
        status = codeasus.projects.bank.eco.domain.local.model.enums.TransactionStatus.valueOf(this.status),
        createdAt = LocalDateTime.parse(this.createdAt, formatter),
        updatedAt = LocalDateTime.parse(this.updatedAt, formatter)
    )
}