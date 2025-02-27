package codeasus.projects.bank.eco.core.navigation.ui

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import codeasus.projects.bank.eco.R
import codeasus.projects.bank.eco.core.ui.shared.view.PersonalProfile
import codeasus.projects.bank.eco.core.ui.shared.view.utils.DataSourceDefaults
import codeasus.projects.bank.eco.domain.local.model.user.UserModel


@Composable
fun ProfileAction(user: UserModel, onProfileClick: () -> Unit) {
    IconButton(onClick = onProfileClick) {
        PersonalProfile(
            imageModifier = Modifier
                .size(36.dp)
                .clip(CircleShape),
            profileImageResId = user.profileImageResId
        )
    }
}

@Composable
fun NotificationAction(onNotificationClick: () -> Unit) {
    BadgedBox(
        modifier = Modifier.size(48.dp),
        badge = {
        Badge(
            containerColor = MaterialTheme.colorScheme.primary
        )
    }) {
        IconButton(onClick = onNotificationClick) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(R.drawable.ic_notification),
                contentDescription = Icons.Outlined.Notifications.name
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActionPreview() {
    NotificationAction { }
    ProfileAction(DataSourceDefaults.unknownUser) { }
}