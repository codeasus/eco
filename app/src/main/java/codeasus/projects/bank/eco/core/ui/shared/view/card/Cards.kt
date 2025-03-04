package codeasus.projects.bank.eco.core.ui.shared.view.card

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import codeasus.projects.bank.eco.core.ui.shared.view.utils.DataSourceDefaults
import codeasus.projects.bank.eco.core.ui.theme.EcoTheme
import codeasus.projects.bank.eco.domain.local.model.user.UserBankAccountModel

@Composable
fun Cards(
    userBankAccounts: List<UserBankAccountModel>,
    onCardSelected: (UserBankAccountModel) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = if (userBankAccounts.size == 1) Arrangement.Center else Arrangement.spacedBy(16.dp)
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

@Preview(
    showSystemUi = false,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun CardsCarouselPreview() {
    EcoTheme {
        Cards(DataSourceDefaults.unknownUser.bankAccounts) {}
    }
}