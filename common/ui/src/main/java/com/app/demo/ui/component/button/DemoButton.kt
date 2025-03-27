package com.app.demo.ui.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.demo.ui.component.Loader
import com.app.demo.ui.theme.DemoTheme
import com.app.demo.ui.theme.Neutral00
import com.app.demo.ui.theme.Neutral90

@Composable
fun DemoButton(
    modifier: Modifier = Modifier,
    text: String,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    shape: RoundedCornerShape = DemoTheme.shapes.m,
    border: BorderStroke? = null,
    buttonColors: DemoButtonColors = DemoButtonColors.primary(),
    contentPadding: PaddingValues = PaddingValues(),
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onClick: () -> Unit,
) {
    val textColor = when {
        isEnabled -> buttonColors.enabledTextColor
        else -> buttonColors.disabledTextColor
    }

    val defaultButtonColors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = buttonColors.enabledContainerColor,
        disabledContainerColor = buttonColors.disabledContainerColor,
    )

    Button(
        modifier = modifier,
        shape = shape,
        border = border,
        colors = defaultButtonColors,
        enabled = isEnabled,
        contentPadding = contentPadding,
        onClick = { if (!isLoading) onClick.invoke() },
    ) {
        if (isLoading) {
            Loader(
                modifier = Modifier.size(20.dp),
                tint = buttonColors.loaderColor,
            )
        } else {
            if (leadingIcon != null) {
                leadingIcon.invoke()
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = text,
                textAlign = TextAlign.Center,
                style = DemoTheme.typography.H5XL,
                color = textColor,
            )
            if (trailingIcon != null) {
                Spacer(modifier = Modifier.width(8.dp))
                trailingIcon.invoke()
            }
        }
    }
}

@Immutable
data class DemoButtonColors(
    val enabledContainerColor: Color,
    val disabledContainerColor: Color,
    val enabledTextColor: Color,
    val disabledTextColor: Color,
    val loaderColor: Color
) {
    companion object {
        @Composable
        fun primary(): DemoButtonColors {
            return DemoButtonColors(
                enabledContainerColor = DemoTheme.colors.buttonPrimary,
                disabledContainerColor = DemoTheme.colors.buttonDisabled,
                enabledTextColor = DemoTheme.colors.surface,
                disabledTextColor = DemoTheme.colors.textDisabled,
                loaderColor = DemoTheme.colors.surface
            )
        }
        @Composable
        fun secondary(): DemoButtonColors {
            return DemoButtonColors(
                enabledContainerColor = DemoTheme.colors.buttonSecondary,
                disabledContainerColor = DemoTheme.colors.buttonDisabled,
                enabledTextColor = DemoTheme.colors.surface,
                disabledTextColor = DemoTheme.colors.textDisabled,
                loaderColor = DemoTheme.colors.surface
            )
        }
    }
}

@Preview
@Composable
private fun DemoButtonPreview() {
    DemoTheme {
        Surface {
            DemoButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Button",
                onClick = {},
            )
        }
    }
}


@Preview
@Composable
private fun DemoButtonLoadingPreview() {
    DemoTheme {
        Surface {
            DemoButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Button",
                isLoading = true,
                onClick = {},
            )
        }
    }
}

@Preview
@Composable
private fun DemoButtonDisabledPreview() {
    DemoTheme {
        Surface {
            DemoButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Button",
                isEnabled = false,
                onClick = {},
            )
        }
    }
}

