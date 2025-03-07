package codeasus.projects.bank.eco.core.ui.shared.view.base

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun BaseScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = topBar,
        bottomBar = bottomBar,
        containerColor = MaterialTheme.colorScheme.surface
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content()
        }
    }
}