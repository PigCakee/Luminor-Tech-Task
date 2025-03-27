package com.app.demo.ui.component.inputfield

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults.Container
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.demo.ui.model.UiText
import com.app.demo.ui.theme.DemoTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun DemoInputFieldEmail(
    modifier: Modifier = Modifier,
    value: String,
    hint: String = "",
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    maxLines: Int = 1,
    isError: Boolean = false,
    isSuccess: Boolean = false,
    label: String? = null,
    isFocusable: Boolean = true,
    enabled: Boolean = true,
    errorMessages: ImmutableList<UiText>? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit = {},
    onFocusChange: (Boolean) -> Unit = {},
) {
    DemoInputField(
        modifier = modifier,
        value = value,
        hint = hint,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions.copy(
            keyboardType = KeyboardType.Email,
        ),
        keyboardActions = keyboardActions,
        maxLines = maxLines,
        isError = isError,
        isSuccess = isSuccess,
        label = label,
        isFocusable = isFocusable,
        enabled = enabled,
        errorMessages = errorMessages,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        prefix = prefix,
        suffix = suffix,
        onValueChange = onValueChange,
        onFocusChange = onFocusChange,
    )
}

@Composable
fun DemoInputFieldPassword(
    modifier: Modifier = Modifier,
    value: String,
    hint: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    maxLines: Int = 1,
    isError: Boolean = false,
    isSuccess: Boolean = false,
    label: String? = null,
    isFocusable: Boolean = true,
    enabled: Boolean = true,
    errorMessages: ImmutableList<UiText>? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit = {},
    onFocusChange: (Boolean) -> Unit = {},
) {
    DemoInputField(
        modifier = modifier,
        value = value,
        hint = hint,
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = keyboardOptions.copy(
            keyboardType = KeyboardType.Password,
        ),
        keyboardActions = keyboardActions,
        maxLines = maxLines,
        isError = isError,
        isSuccess = isSuccess,
        label = label,
        isFocusable = isFocusable,
        enabled = enabled,
        errorMessages = errorMessages,
        leadingIcon = leadingIcon,
        prefix = prefix,
        suffix = suffix,
        onValueChange = onValueChange,
        onFocusChange = onFocusChange,
    )
}

@Composable
fun DemoInputField(
    modifier: Modifier = Modifier,
    value: String,
    hint: String = "",
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    maxLines: Int = 1,
    contentPadding: PaddingValues = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
    textStyle: TextStyle = DemoTheme.typography.H5XL,
    isError: Boolean = false,
    isSuccess: Boolean = false,
    label: String? = null,
    isFocusable: Boolean = true,
    enabled: Boolean = true,
    errorMessages: ImmutableList<UiText>? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit = {},
    onFocusChange: (Boolean) -> Unit = {},
) {
    var textFieldValueState by remember { mutableStateOf(TextFieldValue(text = value)) }
    val textFieldValue = textFieldValueState.copy(text = value)

    SideEffect {
        if (textFieldValue.selection != textFieldValueState.selection ||
            textFieldValue.composition != textFieldValueState.composition
        ) {
            textFieldValueState = textFieldValue
        }
    }
    var lastTextValue by remember(value) { mutableStateOf(value) }

    DemoInputField(
        modifier = modifier,
        value = textFieldValue,
        hint = hint,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        maxLines = maxLines,
        contentPadding = contentPadding,
        textStyle = textStyle,
        isError = isError,
        isSuccess = isSuccess,
        label = label,
        isFocusable = isFocusable,
        enabled = enabled,
        errorMessages = errorMessages,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        prefix = prefix,
        suffix = suffix,
        onValueChange = { newTextFieldValueState ->
            textFieldValueState = newTextFieldValueState

            val stringChangedSinceLastInvocation = lastTextValue != newTextFieldValueState.text
            lastTextValue = newTextFieldValueState.text

            if (stringChangedSinceLastInvocation) {
                onValueChange(newTextFieldValueState.text)
            }
        },
        onFocusChange = onFocusChange
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DemoInputField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    hint: String = "",
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    maxLines: Int = 1,
    contentPadding: PaddingValues = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
    textStyle: TextStyle = DemoTheme.typography.H5XL,
    isError: Boolean = false,
    isSuccess: Boolean = false,
    label: String? = null,
    isFocusable: Boolean = true,
    enabled: Boolean = true,
    errorMessages: ImmutableList<UiText>? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    onValueChange: (TextFieldValue) -> Unit = {},
    onFocusChange: (Boolean) -> Unit = {},
) {
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }

    val colors = OutlinedTextFieldDefaults.colors(
        cursorColor = DemoTheme.colors.textPrimary,
        errorTextColor = DemoTheme.colors.error,
        focusedTextColor = DemoTheme.colors.textPrimary,
        unfocusedTextColor = DemoTheme.colors.textPrimary,
        errorBorderColor = DemoTheme.colors.error,
        focusedBorderColor = when {
            isError -> DemoTheme.colors.error
            isSuccess -> DemoTheme.colors.success
            else -> DemoTheme.colors.textFieldBorderFocused
        },
        unfocusedBorderColor = if (!isSuccess) DemoTheme.colors.textFieldBorder else DemoTheme.colors.success,
        disabledContainerColor = DemoTheme.colors.textFieldBackgroundDisabled,
        disabledBorderColor = Color.Transparent,
        focusedSuffixColor = DemoTheme.colors.textPrimary,
        unfocusedSuffixColor = DemoTheme.colors.textPrimary,
    )

    val secondaryTextColor = if (enabled) DemoTheme.colors.textSecondary
    else DemoTheme.colors.textDisabled

    val singleLine = maxLines == 1

    Column {
        if (label != null) {
            Text(
                text = label,
                style = DemoTheme.typography.P1XL,
                color = secondaryTextColor,
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        val internalModifier: Modifier = if (singleLine) Modifier.height(40.dp)
        else Modifier.heightIn(min = 40.dp)

        BasicTextField(
            value = value,
            modifier = modifier
                .then(internalModifier)
                .onFocusChanged { state ->
                    if (isFocusable) onFocusChange(state.hasFocus)
                }
                .focusable(isFocusable),
            onValueChange = onValueChange,
            textStyle = textStyle.copy(color = DemoTheme.colors.textPrimary),
            cursorBrush = SolidColor(DemoTheme.colors.textPrimary),
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            interactionSource = interactionSource,
            enabled = enabled,
            maxLines = maxLines,
            readOnly = !isFocusable,
            singleLine = singleLine,
            decorationBox = @Composable { innerTextField ->
                OutlinedTextFieldDefaults.DecorationBox(
                    value = value.text,
                    visualTransformation = visualTransformation,
                    innerTextField = innerTextField,
                    placeholder = {
                        Text(
                            text = hint,
                            style = DemoTheme.typography.H5XL,
                            color = DemoTheme.colors.textHint
                        )
                    },
                    interactionSource = interactionSource,
                    enabled = enabled,
                    singleLine = singleLine,
                    contentPadding = contentPadding,
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon,
                    prefix = prefix,
                    suffix = suffix,
                    isError = isError,
                    colors = colors,
                    container = {
                        Container(
                            enabled = enabled,
                            isError = isError,
                            interactionSource = interactionSource,
                            colors = colors,
                            shape = DemoTheme.shapes.xs,
                        )
                    }
                )
            }
        )
        AnimatedVisibility(visible = !errorMessages.isNullOrEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            errorMessages?.forEachIndexed { index, message ->
                Text(
                    modifier = Modifier,
                    text = message.asString(context),
                    style = DemoTheme.typography.P1XL,
                    color = DemoTheme.colors.error
                )
                if (index == errorMessages.lastIndex) return@forEachIndexed
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Preview
@Composable
private fun PreviewInputFieldEmail() {
    DemoTheme {
        Surface {
            DemoInputFieldEmail(
                modifier = Modifier.width(400.dp),
                value = "",
                hint = "Email",
                label = "Email address",
                errorMessages = persistentListOf(
                    UiText.Text("8 or more characters"),
                    UiText.Text("Use all of the following: lowercase, uppercase, numbers and symbols."),
                )
            )
        }
    }
}

@Preview
@Composable
private fun PreviewInputFieldPassword() {
    DemoTheme {
        Surface {
            DemoInputFieldPassword(
                modifier = Modifier.width(400.dp),
                value = "",
                hint = "Password",
                label = "Enter password",
                errorMessages = persistentListOf(
                    UiText.Text("8 or more characters"),
                    UiText.Text("Use all of the following: lowercase, uppercase, numbers and symbols."),
                )
            )
        }
    }
}

@Preview
@Composable
private fun PreviewInputFieldEmpty() {
    DemoTheme {
        Surface {
            DemoInputField(
                modifier = Modifier.width(400.dp),
                value = "",
                hint = "Email",
                label = "Email address",
                errorMessages = persistentListOf(
                    UiText.Text("8 or more characters"),
                    UiText.Text("Use all of the following: lowercase, uppercase, numbers and symbols."),
                )
            )
        }
    }
}

@Preview
@Composable
private fun PreviewInputFieldNotFocusable() {
    DemoTheme {
        Surface {
            DemoInputField(
                modifier = Modifier.width(400.dp),
                value = "email@gmail.com",
                label = "Email address",
                isFocusable = false,
                errorMessages = persistentListOf(
                    UiText.Text("8 or more characters"),
                    UiText.Text("Use all of the following: lowercase, uppercase, numbers and symbols."),
                )
            )
        }
    }
}

@Preview
@Composable
private fun PreviewInputFieldDisabled() {
    DemoTheme {
        Surface {
            DemoInputField(
                modifier = Modifier.width(400.dp),
                value = "",
                hint = "Email",
                label = "Email address",
                enabled = false,
                errorMessages = persistentListOf(
                    UiText.Text("8 or more characters"),
                    UiText.Text("Use all of the following: lowercase, uppercase, numbers and symbols."),
                )
            )
        }
    }
}

@Preview
@Composable
private fun PreviewInputFieldNeutral() {
    DemoTheme {
        Surface {
            DemoInputField(
                modifier = Modifier.width(400.dp),
                value = "email@gmail.com",
                label = "Email",
                errorMessages = persistentListOf(
                    UiText.Text("8 or more characters"),
                    UiText.Text("Use all of the following: lowercase, uppercase, numbers and symbols."),
                )
            )
        }
    }
}

@Preview
@Composable
private fun PreviewInputFieldSuccess() {
    DemoTheme {
        Surface {
            DemoInputField(
                modifier = Modifier.width(400.dp),
                value = "email@gmail.com",
                isSuccess = true,
                label = "Email",
                errorMessages = persistentListOf(
                    UiText.Text("8 or more characters"),
                    UiText.Text("Use all of the following: lowercase, uppercase, numbers and symbols."),
                )
            )
        }
    }
}

@Preview
@Composable
private fun PreviewInputFieldSuccessDisabled() {
    DemoTheme {
        Surface {
            DemoInputField(
                modifier = Modifier.width(400.dp),
                value = "email@gmail.com",
                isSuccess = true,
                enabled = false,
                label = "Email",
                errorMessages = persistentListOf(
                    UiText.Text("8 or more characters"),
                    UiText.Text("Use all of the following: lowercase, uppercase, numbers and symbols."),
                )
            )
        }
    }
}

@Preview
@Composable
private fun PreviewInputFieldFailed() {
    DemoTheme {
        Surface {
            DemoInputField(
                modifier = Modifier.width(400.dp),
                value = "email@gmail.com",
                isError = true,
                label = "Email",
                errorMessages = persistentListOf(
                    UiText.Text("8 or more characters"),
                    UiText.Text("Use all of the following: lowercase, uppercase, numbers and symbols."),
                )
            )
        }
    }
}

@Preview
@Composable
private fun PreviewInputFieldFailedDisabled() {
    DemoTheme {
        Surface {
            DemoInputField(
                modifier = Modifier.width(400.dp),
                value = "email@gmail.com",
                isError = true,
                enabled = false,
                label = "Email",
                errorMessages = persistentListOf(
                    UiText.Text("8 or more characters"),
                    UiText.Text("Use all of the following: lowercase, uppercase, numbers and symbols."),
                )
            )
        }
    }
}
