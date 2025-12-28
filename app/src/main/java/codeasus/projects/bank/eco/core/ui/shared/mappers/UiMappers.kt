package codeasus.projects.bank.eco.core.ui.shared.mappers

import codeasus.projects.bank.eco.core.ui.shared.view.models.BankAccountUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.CustomerBankAccountUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.CustomerUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.SystemMessageUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.TransactionUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.UserUi
import codeasus.projects.bank.eco.core.ui.shared.view.utils.formatBankAccountNumber
import codeasus.projects.bank.eco.core.ui.shared.view.utils.formatExpiryDate
import codeasus.projects.bank.eco.core.ui.shared.view.utils.formatFullLocalDateTime
import codeasus.projects.bank.eco.core.ui.shared.view.utils.formatLocalDateTime
import codeasus.projects.bank.eco.core.ui.shared.view.utils.formatTransactionAmount
import codeasus.projects.bank.eco.domain.local.model.customer.CustomerModel
import codeasus.projects.bank.eco.domain.local.model.system_message.SystemMessageModel
import codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel
import codeasus.projects.bank.eco.domain.local.model.user.UserBankAccountModel
import codeasus.projects.bank.eco.domain.local.model.user.UserModel

fun UserBankAccountModel.toBankAccountUi(): BankAccountUi {
    return BankAccountUi(
        id = this.id.toString(),
        name = this.name,
        number = formatBankAccountNumber(this.number),
        type = this.type,
        scheme = this.scheme,
        balance = formatTransactionAmount(this.balance),
        currency = this.currency,
        cvv = this.cvv,
        expiryDate = formatExpiryDate(this.expiryDate)
    )
}

fun TransactionModel.toTransactionUi(): TransactionUi {
    return TransactionUi(
        id = this.id.toString(),
        accountIdSelf = this.accountIdSelf,
        accountNumberFrom = this.accountNumberFrom,
        accountNumberTo = this.accountNumberTo,
        amount = formatTransactionAmount(this.amount),
        currency = this.currency,
        rate = this.rate,
        type = this.type,
        status = this.status,
        createdAt = formatLocalDateTime(this.createdAt),
        updatedAt = formatLocalDateTime(this.updatedAt)
    )
}

fun CustomerModel.toCustomerUi(): CustomerUi {
    return CustomerUi(
        name = this.name,
        profileImg = this.profileImgResId,
        bankAccount = CustomerBankAccountUi(
            name = this.bankAccount.name,
            number = formatBankAccountNumber(this.bankAccount.number)
        )
    )
}

fun SystemMessageModel.toSystemMessageUi(): SystemMessageUi {
    return SystemMessageUi(
        title = this.title,
        content = this.content,
        priority = this.priority,
        createdAt = formatFullLocalDateTime(this.createdAt)
    )
}

fun UserModel.toUserUi(): UserUi {
    return UserUi(
        name = this.name,
        tagName = this.tagName,
        profileImageResId = this.profileImageResId
    )
}