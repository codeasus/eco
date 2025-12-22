package codeasus.projects.bank.eco.feature.card.presentation.utils

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import codeasus.projects.bank.eco.R
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme

@Composable
fun CardInstantAction(
    @DrawableRes iconResourceId: Int,
    actionName: String,
    enabled: Boolean = false,
    onClick: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(color = Color.White.copy(alpha = 0.2F))
                .clickable(
                    enabled = enabled,
                    onClick = onClick,
                    role = Role.Button
                )
        ) {
            Icon(
                modifier = Modifier.size(28.dp),
                painter = painterResource(id = iconResourceId),
                contentDescription = actionName,
                tint = Color.White
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = actionName, style = TextStyle(fontSize = 12.sp), color = Color.White)
    }
}

@Preview(showSystemUi = false, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun CardInstantActionPreview() {
    EcoTheme {
        CardInstantAction(iconResourceId = R.drawable.ic_flip, actionName = "Flip") { }
    }
}