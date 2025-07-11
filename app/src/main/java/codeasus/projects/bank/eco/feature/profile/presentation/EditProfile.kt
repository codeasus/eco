package codeasus.projects.bank.eco.feature.profile.presentation

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import codeasus.projects.bank.eco.R
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme

@Composable
fun EditProfile(@DrawableRes imageRes: Int, size: Dp, onEditClick: () -> Unit) {
    Box(modifier = Modifier.size(size), contentAlignment = Alignment.BottomEnd) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape),
            painter = painterResource(imageRes),
            contentDescription = "Profile Image",
            contentScale = ContentScale.Crop
        )
        FilledIconButton(
            modifier = Modifier.size(32.dp),
            onClick = onEditClick
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription = "Edit Profile Image"
            )
        }
    }
}

@Preview(
    showSystemUi = false,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = false
)
@Composable
fun EditProfileImageViewPreview() {
    EcoTheme {
        EditProfile(imageRes = R.drawable.bruce_wayne, 86.dp) {

        }
    }
}