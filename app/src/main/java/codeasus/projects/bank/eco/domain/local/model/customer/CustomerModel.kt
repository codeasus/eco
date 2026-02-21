package codeasus.projects.bank.eco.domain.local.model.customer

data class CustomerModel (
    val name: String,
    val isFriend: Boolean = false,
    val profileImgResId: Int, 
    val bankAccount: CustomerBankAccountModel
)