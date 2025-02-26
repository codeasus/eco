package codeasus.projects.bank.eco.feature.transfer.presentation

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

@Composable
fun TermsAndConditionsText(termsUrl: String) {
    val context = LocalContext.current
    val annotatedString = buildAnnotatedString {
        val firstPart = "I agree with "
        addStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            start = 0,
            end = firstPart.length - 1
        )
        append(firstPart)
        val startIndex = length
        append("Terms and Conditions")
        addStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline
            ),
            start = startIndex,
            end = length
        )

        addStringAnnotation(
            tag = "URL",
            annotation = termsUrl,
            start = startIndex,
            end = length
        )
    }

    BasicText(
        text = annotatedString,
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(termsUrl))
                context.startActivity(intent)
            }
    )
}