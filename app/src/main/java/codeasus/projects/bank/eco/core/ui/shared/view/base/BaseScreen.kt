package codeasus.projects.bank.eco.core.ui.shared.view.base

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel

@Composable
inline fun <reified T : ViewModel> BaseScreen(crossinline content: @Composable (T) -> Unit) {
    val viewModel: T = hiltViewModel()
    content(viewModel)
}