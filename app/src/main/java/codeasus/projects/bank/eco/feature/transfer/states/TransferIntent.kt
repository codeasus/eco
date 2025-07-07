package codeasus.projects.bank.eco.feature.transfer.states

import codeasus.projects.bank.eco.domain.local.model.customer.CustomerModel
import codeasus.projects.bank.eco.domain.local.model.enums.Currency

sealed class TransferIntent {
    data class SelectCurrency(val currency: Currency) : TransferIntent()
    data class SetTransferAmount(val amount: String) : TransferIntent()
    data class SetBeneficiaryName(val beneficiaryName: String) : TransferIntent()
    data class SetAccountNumber(val accountNumber: String): TransferIntent()
    data class SelectCustomer(val customerModel: CustomerModel): TransferIntent()
}