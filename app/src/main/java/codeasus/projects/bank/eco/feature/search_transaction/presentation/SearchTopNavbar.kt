package codeasus.projects.bank.eco.feature.search_transaction.presentation

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import codeasus.projects.bank.eco.R
import codeasus.projects.bank.eco.core.navigation.ui.SearchAction
import codeasus.projects.bank.eco.feature.search_transaction.presentation.SearchTopNavbarDefaults.horizontalPadding
import codeasus.projects.bank.eco.feature.search_transaction.presentation.SearchTopNavbarDefaults.topBarHeight
import codeasus.projects.bank.eco.feature.search_transaction.presentation.SearchTopNavbarDefaults.topBarHeightExpanded
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme
import codeasus.projects.bank.eco.feature.search_transaction.states.SearchTransactionState

object SearchTopNavbarDefaults {
    val horizontalPadding = 4.dp
    val topBarHeight = 64.dp
    val topBarHeightExpanded = 128.dp
}

@Composable
fun SearchTopNavbar(
    title: String,
    searchTransactionState: SearchTransactionState,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSearchTextValueChange: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .height(if (searchTransactionState.isSearchTextVisible) topBarHeightExpanded else topBarHeight),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = horizontalPadding, vertical = 9.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Box(modifier = Modifier.fillMaxHeight()) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_nav_back),
                            contentDescription = "Back",
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = horizontalPadding),
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                    )
                }

                Row(
                    modifier = Modifier.fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { onSearchClick() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "Search",
                        )
                    }
                }
            }

            AnimatedVisibility(
                visible = searchTransactionState.isSearchTextVisible,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                ) {
                    OutlinedTextField(
                        value = searchTransactionState.searchText,
                        onValueChange = { onSearchTextValueChange(it) },
                        placeholder = { Text(text = "Search transactions") },
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent
                        ),
                        leadingIcon = {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(R.drawable.ic_search),
                                contentDescription = "Search icon"
                            )
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReferenceTopBar(scrollBehavior: TopAppBarScrollBehavior) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface),
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_nav_back),
                    contentDescription = "Back"
                )
            }
        },
        title = {
            Text(
                text = "Home",
                style = MaterialTheme.typography.titleLarge,
            )
        },
        actions = {
            SearchAction {}
        }
    )
}

@Preview(
    showSystemUi = false,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun SearchTopNavbarPreview() {
    EcoTheme {
        SearchTopNavbar(
            "Transactions",
            SearchTransactionState(),
            onBackClick = {},
            onSearchClick = {},
            onSearchTextValueChange = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    showSystemUi = false,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun ReferenceTopNavbarPreview() {
    EcoTheme {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
        ReferenceTopBar(scrollBehavior)
    }
}