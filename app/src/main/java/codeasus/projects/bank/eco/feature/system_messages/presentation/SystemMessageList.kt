package codeasus.projects.bank.eco.feature.system_messages.presentation

import android.content.res.Configuration
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import codeasus.projects.bank.eco.core.ui.shared.view.models.SystemMessageUi
import codeasus.projects.bank.eco.core.ui.shared.view.utils.DataSourceDefaults
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme

@Composable
fun SystemMessageList(systemMessages: List<SystemMessageUi>) {
    LazyColumn {
        items(systemMessages) { systemMessage ->
            SystemMessageListItem(systemMessage)
        }
    }
}

@Preview(
    showSystemUi = false,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true
)
@Composable
fun SystemMessageListsPreview() {
    EcoTheme {
        SystemMessageList(DataSourceDefaults.getSystemMessages())
    }
}