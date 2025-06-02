package codeasus.projects.bank.eco.feature.card.presentation.utils

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import codeasus.projects.bank.eco.R
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme

@Composable
fun CardManagementAction(
    cardManagementAction: CardManagementActionUIData,
    onClick: (actionId: Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .clickable {
                onClick(cardManagementAction.actionId)
            }
            .padding(horizontal = 4.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            modifier = Modifier
                .border(1.dp, MaterialTheme.colorScheme.outline, CircleShape)
                .padding(18.dp),
            painter = painterResource(id = cardManagementAction.actionIcon),
            contentDescription = cardManagementAction.actionName
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = cardManagementAction.actionName)
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.ic_nav_forward),
            contentDescription = "Next"
        )
    }
}

@Preview(showSystemUi = false, showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun CardManagementActionPreview() {
    EcoTheme {
        CardManagementAction(CardManagementActions.cardActions[3]) { }
    }
}