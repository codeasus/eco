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
import codeasus.projects.bank.eco.core.ui.shared.view.utils.FakeDataSource
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme
import codeasus.projects.bank.eco.domain.local.model.customer.CustomerModel

@Composable
fun Profile(
    imageModifier: Modifier,
    customer: CustomerModel,
    withName: Boolean = false,
    isSelected: Boolean = false,
    onProfileSelected: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(64.dp)
            .clickable(onClick = { onProfileSelected() })
                then (
                if (isSelected) {
                    Modifier
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(4.dp)
                } else Modifier
                ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = imageModifier,
            painter = painterResource(customer.profileImgResId),
            contentScale = ContentScale.Crop,
            contentDescription = "${customer.name}'s photo"
        )
        if (withName) {
            Text(text = customer.name, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
fun PersonalProfile(
    containerModifier: Modifier? = null,
    imageModifier: Modifier,
    user: codeasus.projects.bank.eco.domain.local.model.user.UserModel,
    withName: Boolean = false
) {
    Column(
        modifier = containerModifier ?: Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = imageModifier,
            painter = painterResource(user.profileImageResId),
            contentScale = ContentScale.Crop,
            contentDescription = "${user.name}'s photo"
        )
        if (withName) {
            Text(text = user.name, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Preview(showSystemUi = false, showBackground = false)
@Composable
fun ProfilePreview() {
    EcoTheme {
        Profile(
            imageModifier = Modifier
                .size(32.dp)
                .clip(CircleShape),
            customer = FakeDataSource.getCustomers()[0],
            withName = true
        ) {}
    }
}