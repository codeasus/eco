package codeasus.projects.bank.eco.domain.local.model.user

data class UserModel(
    val name: String,
    val profileImageResId: Int,
    val bankAccounts: List<UserBankAccountModel>
)
