package codeasus.projects.bank.eco.core.navigation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import codeasus.projects.bank.eco.R
import codeasus.projects.bank.eco.core.ui.shared.view.PersonalProfile
import codeasus.projects.bank.eco.core.ui.shared.view.utils.FakeDataSource
import codeasus.projects.bank.eco.core.ui.shared.viewmodel.user.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenericTopNavbar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    title: String,
    viewModel: UserViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    val user by viewModel.userState.collectAsState()

    TopAppBar(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)),
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(
                alpha = 0.25f
            )
        ),
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilledIconButton(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape),
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    onClick = onBackClick
                ) {
                    Icon(
                        modifier = Modifier.size(14.dp),
                        painter = painterResource(R.drawable.ic_nav_back),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        contentDescription = "App Icon",
                    )
                }
                Spacer(Modifier.width(12.dp))
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.titleMedium.fontSize
                    )
                )
            }
        },
        actions = {
            FilledIconButton(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape),
                colors = IconButtonDefaults.filledIconButtonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                onClick = onSearchClick
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(R.drawable.ic_search),
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = "Search",
                )
            }
            BadgedBox(
                modifier = Modifier.size(42.dp),
                badge = {
                    Badge(containerColor = MaterialTheme.colorScheme.primary)
                }
            ) {
                IconButton(onClick = onNotificationClick) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(R.drawable.ic_notification_bell),
                        contentDescription = "Notification"
                    )
                }
            }
            IconButton(
                modifier = Modifier.padding(end = 12.dp),
                onClick = onProfileClick
            ) {
                PersonalProfile(
                    imageModifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape),
                    user = codeasus.projects.bank.eco.domain.local.model.user.UserModel(
                        profileImageResId = user?.profileImageResId
                            ?: FakeDataSource.unknownUser.profileImageResId,
                        name = user?.name ?: FakeDataSource.unknownUser.name,
                        bankAccounts = user?.bankAccounts ?: FakeDataSource.unknownUser.bankAccounts
                    )
                )
            }
        }
    )
}
