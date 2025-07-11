package codeasus.projects.bank.eco.feature.profile.presentation.states

import codeasus.projects.bank.eco.core.ui.shared.view.utils.DataSourceDefaults
import codeasus.projects.bank.eco.domain.local.model.user.UserModel

data class ProfileState(val user: UserModel = DataSourceDefaults.exampleUser)
