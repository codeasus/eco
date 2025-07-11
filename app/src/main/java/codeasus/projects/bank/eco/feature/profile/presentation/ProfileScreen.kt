package codeasus.projects.bank.eco.feature.profile.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import codeasus.projects.bank.eco.core.navigation.NavigationManager
import codeasus.projects.bank.eco.core.navigation.Profile
import codeasus.projects.bank.eco.core.ui.shared.view.MenuItem
import codeasus.projects.bank.eco.core.ui.shared.view.base.BaseBackNavTopNavbar
import codeasus.projects.bank.eco.core.ui.shared.view.base.BaseScaffold
import codeasus.projects.bank.eco.core.ui.shared.view.base.BaseScreen
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme
import codeasus.projects.bank.eco.feature.profile.presentation.states.ProfileIntent
import codeasus.projects.bank.eco.feature.profile.presentation.states.ProfileState
import codeasus.projects.bank.eco.feature.profile.presentation.utils.AccountMenuItems
import codeasus.projects.bank.eco.feature.profile.presentation.utils.AppMenuItems

@Composable
fun ProfileScreenRoot(navigationManager: NavigationManager) {
    BaseScreen<ProfileViewModel> { vm ->
        val state = vm.state.collectAsStateWithLifecycle()

        ProfileScreen(state.value, vm::handleIntent) {
            navigationManager.navigateUp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    state: ProfileState,
    onAction: (ProfileIntent) -> Unit,
    onBackClick: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    BaseScaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            BaseBackNavTopNavbar(scrollBehavior = scrollBehavior, Profile.title) {
                onBackClick()
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EditProfile(imageRes = state.user.profileImageResId, 86.dp) {

            }
            Text(
                text = state.user.name,
                style = TextStyle(fontSize = MaterialTheme.typography.headlineLarge.fontSize)
            )

            TagChip(tagName = state.user.tagName) { }

            Spacer(modifier = Modifier.height(36.dp))

            Text(
                modifier = Modifier.align(Alignment.Start),
                text = "Your account",
                style = TextStyle(fontSize = MaterialTheme.typography.headlineSmall.fontSize)
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                AccountMenuItems.value.forEach { profileMenuItem ->
                    MenuItem(profileMenuItem) {

                    }
                }
            }

            Text(
                modifier = Modifier.align(Alignment.Start),
                text = "Your app",
                style = TextStyle(fontSize = MaterialTheme.typography.headlineSmall.fontSize)
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                AppMenuItems.value.forEach { appMenuItem ->
                    MenuItem(appMenuItem) {

                    }
                }
            }
        }
    }
}

@Preview(
    showSystemUi = false,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun SystemMessagesPreview() {
    EcoTheme {
        ProfileScreen(
            state = ProfileState(),
            onAction = {}
        ) {

        }
    }
}