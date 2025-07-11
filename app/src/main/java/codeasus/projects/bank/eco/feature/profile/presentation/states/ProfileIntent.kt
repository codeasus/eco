package codeasus.projects.bank.eco.feature.profile.presentation.states

sealed class ProfileIntent {
    data object LoadUserData: ProfileIntent()
}