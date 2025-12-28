package codeasus.projects.bank.eco.core.ui.shared.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import codeasus.projects.bank.eco.core.ui.shared.view.models.CustomerUi
import codeasus.projects.bank.eco.core.ui.shared.view.utils.DataSourceDefaults
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme

@Composable
fun Profiles(customers: List<CustomerUi>, onProfileSelected: (CustomerUi) -> Unit) {
    var selectedCustomer by remember { mutableStateOf<String?>(null) }

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            count = customers.size,
            contentType = { i -> customers[i] }
        ) { index ->
            val customer = customers[index]
            val isSelected = selectedCustomer == customer.name
            Profile(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .then(
                        if (isSelected) {
                            Modifier
                                .border(
                                    width = 2.dp,
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = CircleShape
                                )
                        } else Modifier
                    ),
                customer = customers[index],
                withName = true,
                isSelected = isSelected,
                onProfileSelected = {
                    selectedCustomer = customer.name
                    onProfileSelected(customers[index])
                }
            )
        }
    }
}

@Preview(showSystemUi = false, showBackground = false)
@Composable
fun ProfilesPreview() {
    EcoTheme {
        Profiles(DataSourceDefaults.getCustomers()) {
        }
    }
}