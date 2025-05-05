package codeasus.projects.bank.eco.core.navigation
import kotlinx.serialization.Serializable

interface Screen {
    val title: String
}

@Serializable
data object SearchTransaction : Screen {
    override val title: String = "Transactions"
}

@Serializable
data class Card(val bankAccountId: String) : Screen {
    override val title: String = "Card"
}

sealed interface BottomNavbarScreen : Screen {
    @Serializable
    data object Home: BottomNavbarScreen {
        override val title: String = "Home"
    }

    @Serializable
    data object Transfer: BottomNavbarScreen {
        override val title: String = "Transfer"
    }

    @Serializable
    data object Product: BottomNavbarScreen {
        override val title: String = "Product"
    }
}