package codeasus.projects.bank.eco.core.ui.shared.view.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Cards(userBankAccounts: List<codeasus.projects.bank.eco.domain.local.model.user.UserBankAccountModel>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp),
        horizontalArrangement = if (userBankAccounts.size == 1) Arrangement.Center else Arrangement.spacedBy(
            16.dp
        )
    ) {
        items(
            count = userBankAccounts.size,
            contentType = { i -> userBankAccounts[i] }
        ) { index ->
            if (index % 2 == 0) {
                BankCardDark(userBankAccounts[index])
            } else {
                BankCardLight(userBankAccounts[index])
            }
        }
    }
}