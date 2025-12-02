package codeasus.projects.bank.eco.domain.local.model.customer

data class CustomerModel (
    val name: String,
    val profileImgResId: Int,
    val bankAccount: CustomerBankAccountModel
)