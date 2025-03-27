package com.app.demo.feature.auth.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.demo.ui.theme.DemoTheme

@Composable
fun PasswordValidationHintItem(
    hint: String,
    isSatisfied: Boolean?,
    modifier: Modifier = Modifier,
) {
    val verificationPrefix = if (isSatisfied == false) "✗" else "✓"

    val highlightColor = when (isSatisfied) {
        true -> DemoTheme.colors.success
        false -> DemoTheme.colors.error
        null -> DemoTheme.colors.textSecondary
    }
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
            .then(modifier),
        text = buildString {
            append(verificationPrefix)
            append(" ")
            append(hint)
        },
        color = highlightColor,
        style = DemoTheme.typography.P1MD,
    )
}

@Preview(showBackground = true)
@Composable
fun PasswordValidationHintItemPreviewSuccess() {
    DemoTheme {
        PasswordValidationHintItem(hint = "At least 8 characters", isSatisfied = true)
    }
}

@Preview(showBackground = true)
@Composable
fun PasswordValidationHintItemPreviewError() {
    DemoTheme {
        PasswordValidationHintItem(hint = "At least 1 digit", isSatisfied = false)
    }
}

@Preview(showBackground = true)
@Composable
fun PasswordValidationHintItemPreviewNeutral() {
    DemoTheme {
        PasswordValidationHintItem(hint = "At least one symbol", isSatisfied = null)
    }
}