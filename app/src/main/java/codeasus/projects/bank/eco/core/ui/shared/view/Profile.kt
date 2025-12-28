package codeasus.projects.bank.eco.core.ui.shared.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import codeasus.projects.bank.eco.core.ui.shared.view.models.CustomerUi
import codeasus.projects.bank.eco.core.ui.shared.view.utils.DataSourceDefaults
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme

private fun Modifier.withName(withName: Boolean): Modifier {
    return if (withName) this.width(64.dp) else this
}

@Composable
fun Profile(
    modifier: Modifier,
    customer: CustomerUi,
    withName: Boolean = false,
    isSelected: Boolean = false,
    onProfileSelected: () -> Unit
) {
    val shape = RoundedCornerShape(12.dp)

    Column(
        modifier = Modifier
            .withName(withName = withName)
            .then(
                if (isSelected) {
                    Modifier.background(
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                        shape = shape
                    )
                } else {
                    Modifier
                }
            )
            .clip(shape)
            .clickable(onClick = onProfileSelected)
            .padding(4.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = modifier,
            painter = painterResource(customer.profileImg),
            contentScale = ContentScale.Crop,
            contentDescription = "${customer.name}'s photo"
        )
        if (withName) {
            Text(text = customer.name, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
fun ImgProfile(modifier: Modifier, profileImageResId: Int) {
    Image(
        modifier = modifier,
        painter = painterResource(profileImageResId),
        contentScale = ContentScale.Crop,
        contentDescription = "User's photo"
    )
}

@Preview(showSystemUi = false, showBackground = false)
@Composable
fun ProfilePreview() {
    EcoTheme {
        Profile(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape),
            customer = DataSourceDefaults.getCustomers()[1],
            isSelected = true,
            withName = true,
        ) {
            
        }
    }
}