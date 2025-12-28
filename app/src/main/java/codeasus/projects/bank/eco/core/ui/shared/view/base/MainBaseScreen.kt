package codeasus.projects.bank.eco.core.ui.shared.view.base

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import codeasus.projects.bank.eco.core.navigation.NavigationManager
import codeasus.projects.bank.eco.core.ui.shared.viewmodel.base.BaseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
inline fun <reified T : BaseViewModel> MainBaseScreen(navigationManager: NavigationManager, crossinline content: @Composable (NavigationManager, T) -> Unit) {
    val viewModel: T = hiltViewModel()
    content(navigationManager,viewModel)
}