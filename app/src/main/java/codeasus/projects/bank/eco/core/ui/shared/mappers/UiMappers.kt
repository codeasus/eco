package codeasus.projects.bank.eco.core.ui.shared.mappers

import codeasus.projects.bank.eco.core.ui.shared.view.models.BankAccountUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.CustomerBankAccountUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.CustomerUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.SystemMessageUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.TransactionUi
import codeasus.projects.bank.eco.core.ui.shared.view.utils.formatBankAccountNumber
import codeasus.projects.bank.eco.core.ui.shared.view.utils.formatExpiryDate
import codeasus.projects.bank.eco.core.ui.shared.view.utils.formatFullLocalDateTime
import codeasus.projects.bank.eco.core.ui.shared.view.utils.formatLocalDateTime
import codeasus.projects.bank.eco.domain.local.model.customer.CustomerModel
import codeasus.projects.bank.eco.domain.local.model.system_message.SystemMessageModel
import codeasus.projects.bank.eco.domain.local.model.transaction.TransactionModel
import codeasus.projects.bank.eco.domain.local.model.user.UserBankAccountModel

fun UserBankAccountModel.toBankAccountUi(): BankAccountUi {
    return BankAccountUi(
        id = this.id,
        name = this.name,
        number = formatBankAccountNumber(this.number),
        type = this.type,
        scheme = this.scheme,
        balance = this.balance,
        cvv = this.cvv,
        expiryDate = formatExpiryDate(this.expiryDate)
    )
}

fun TransactionModel.toTransactionUi(): TransactionUi {
    return TransactionUi(
        id = this.id.toString(),
        internalAccountNumber = this.internalAccountNumber,
        externalAccountNumber = this.externalAccountNumber,
        amount = this.amount,
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