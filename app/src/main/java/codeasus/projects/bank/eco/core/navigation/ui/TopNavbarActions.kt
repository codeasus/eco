package codeasus.projects.bank.eco.core.navigation.ui

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
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
import codeasus.projects.bank.eco.core.ui.shared.view.ImgProfile
import codeasus.projects.bank.eco.core.ui.shared.view.models.UserUi

@Composable
fun SearchAction(onSearchClick: () -> Unit) {
    IconButton(onClick = onSearchClick) {
        Icon(
            painter = painterResource(R.drawable.ic_search),
            tint = MaterialTheme.colorScheme.onSurface,
            contentDescription = Icons.Outlined.Search.name
        )
    }
}

@Composable
fun ProfileAction(user: UserUi, onProfileClick: () -> Unit) {
    IconButton(onClick = onProfileClick) {
        ImgProfile(
            modifier = Modifier
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
                modifier = Modifier.offset((-8).dp, 6.dp),
                containerColor = MaterialTheme.colorScheme.primary
            )
        }
    ) {
        IconButton(onClick = onNotificationClick) {
            Icon(
                painter = painterResource(R.drawable.ic_notification),
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = Icons.Outlined.Notifications.name
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActionPreview() {
    SearchAction { }
}