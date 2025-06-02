package codeasus.projects.bank.eco.feature.card.presentation.utils

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import codeasus.projects.bank.eco.R
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme

@Composable
fun CardInstantAction(@DrawableRes iconResourceId: Int, actionName: String, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        FilledIconButton(
            modifier = Modifier.size(64.dp),
            shape = CircleShape,
            onClick = onClick
        ) {
            Icon(
                modifier = Modifier.size(28.dp),
                painter = painterResource(id = iconResourceId),
                contentDescription = actionName
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = actionName, style = TextStyle(fontSize = 12.sp))
    }
}

@Preview(showSystemUi = false, showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun CardInstantActionPreview() {
    EcoTheme {
        CardInstantAction(iconResourceId = R.drawable.ic_flip, actionName = "Flip") { }
    }
}