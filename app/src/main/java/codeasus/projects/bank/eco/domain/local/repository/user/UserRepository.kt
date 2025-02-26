package codeasus.projects.bank.eco.domain.local.repository.user

import codeasus.projects.bank.eco.domain.local.model.user.UserModel

interface UserRepository {
    suspend fun saveUser(user: UserModel)
    suspend fun loadUser(): UserModel?
}