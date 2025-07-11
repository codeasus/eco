package codeasus.projects.bank.eco.feature.system_messages.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import codeasus.projects.bank.eco.core.navigation.NavigationManager
import codeasus.projects.bank.eco.core.navigation.SystemMessages
import codeasus.projects.bank.eco.core.ui.shared.view.base.BaseBackNavTopNavbar
import codeasus.projects.bank.eco.core.ui.shared.view.base.BaseScaffold
import codeasus.projects.bank.eco.core.ui.shared.view.base.BaseScreen
import codeasus.projects.bank.eco.core.ui.shared.view.utils.DataSourceDefaults
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme
import codeasus.projects.bank.eco.domain.local.model.enums.Priority
import codeasus.projects.bank.eco.feature.system_messages.states.SystemMessagesIntent
import codeasus.projects.bank.eco.feature.system_messages.states.SystemMessagesState

@Composable
fun SystemMessagesScreenRoot(navigationManager: NavigationManager) {
    BaseScreen<SystemMessagesViewModel> { vm ->
        val state = vm.state.collectAsStateWithLifecycle()

        SystemMessagesScreen(state.value, vm::handleIntent) {
            navigationManager.navigateUp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SystemMessagesScreen(
    state: SystemMessagesState,
    onAction: (SystemMessagesIntent) -> Unit,
    onBackClick: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    BaseScaffold(
        topBar = {
            BaseBackNavTopNavbar(scrollBehavior = scrollBehavior, SystemMessages.title) {
                onBackClick()
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(Priority.entries) { priority ->
                    FilterChip(
                        onClick = { onAction(SystemMessagesIntent.FilterByPriority(priority)) },
                        selected = state.selectedPriority == priority,
                        label = {
                            Text(text = priority.name)
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            labelColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            selectedLabelColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        ),
                        shape = RoundedCornerShape(18.dp)
                    )
                }
            }
            SystemMessageList(state.systemMessages)
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
        SystemMessagesScreen(
            state = SystemMessagesState(
                systemMessages = DataSourceDefaults.getSystemMessages()
            ),
            onAction = {},
            onBackClick = {}
        )
    }
}