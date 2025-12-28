package codeasus.projects.bank.eco.feature.product.presentation.states

import codeasus.projects.bank.eco.core.ui.shared.mappers.toUserUi
import codeasus.projects.bank.eco.core.ui.shared.view.models.UserUi
import codeasus.projects.bank.eco.core.ui.shared.view.utils.DataSourceDefaults

data class ProductState(
    val user: UserUi = DataSourceDefaults.unknownUser.first.toUserUi(),
    val isLoading: Boolean = false,
    val error: String? = null
)
