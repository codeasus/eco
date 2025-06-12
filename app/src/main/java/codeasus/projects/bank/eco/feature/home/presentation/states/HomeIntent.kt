package codeasus.projects.bank.eco.feature.home.presentation.states

sealed class HomeIntent {
    data object ReStackCards : HomeIntent()
}