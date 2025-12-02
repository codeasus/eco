package codeasus.projects.bank.eco.core.ui.shared.mappers

import codeasus.projects.bank.eco.core.ui.shared.view.models.BankAccountUi
import codeasus.projects.bank.eco.core.ui.shared.view.utils.formatBankAccountNumber
import codeasus.projects.bank.eco.core.ui.shared.view.utils.formatExpiryDate
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