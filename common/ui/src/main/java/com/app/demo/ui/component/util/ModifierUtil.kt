package com.app.demo.ui.component.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.Role

@Composable
fun Modifier.noRippleClickable(
    enabled: Boolean = true,
    role: Role? = null,
    onClick: () -> Unit,
): Modifier = this
    .clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = onClick,
        enabled = enabled,
        role = role,
    )

@Composable
fun Modifier.conditional(
    condition: Boolean,
    modifier: @Composable Modifier.() -> Modifier,
): Modifier {
    return if (condition) {
        then(modifier())
    } else {
        this
    }
}

@Composable
fun rememberLambda(key: Any? = null, block: () -> Unit): () -> Unit {
    return remember(key) { block }
}

@Composable
fun <T> rememberLambda(key: Any? = null, block: (T) -> Unit): (T) -> Unit {
    return remember(key) { block }
}