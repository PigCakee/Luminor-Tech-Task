package com.app.demo.feature.auth.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.demo.feature.auth.ui.R
import com.app.demo.feature.auth.ui.components.PasswordValidationHint
import com.app.demo.ui.component.button.DemoButton
import com.app.demo.ui.component.button.DemoButtonColors
import com.app.demo.ui.component.inputfield.DemoInputFieldEmail
import com.app.demo.ui.component.inputfield.DemoInputFieldPassword
import com.app.demo.ui.component.util.noRippleClickable
import com.app.demo.ui.component.util.rememberLambda
import com.app.demo.ui.model.UiText
import com.app.demo.ui.theme.DemoTheme
import com.app.demo.utility.validator.PasswordValidationRule
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf

@Composable
fun AuthScreen() {
    val viewModel = hiltViewModel<AuthViewModel>()
    val state = viewModel.state.collectAsStateWithLifecycle()

    AuthScreenContent(
        state = state,
        onIntent = rememberLambda { it -> viewModel.acceptIntent(it) }
    )
}

@Composable
fun AuthScreenContent(
    state: State<AuthViewModel.UiState>,
    onIntent: (AuthViewModel.UiIntent) -> Unit,
) {
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
    val focusRequesterPassword = remember { FocusRequester() }

    val emailErrors = remember(state.value.isEmailError) {
        if (state.value.isEmailError) {
            persistentListOf(UiText.Id(R.string.error_invalid_email_address))
        } else persistentListOf()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState)
            .noRippleClickable { focusManager.clearFocus() },
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Image(
            modifier = Modifier.size(146.dp),
            painter = painterResource(R.drawable.ic_logo),
            contentDescription = null,
        )
        Text(
            text = stringResource(R.string.authentication),
            style = DemoTheme.typography.H2XL,
            color = DemoTheme.colors.textPrimary
        )
        Column(
            modifier = Modifier.padding(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            DemoInputFieldEmail(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                value = state.value.email,
                onValueChange = { onIntent(AuthViewModel.UiIntent.EmailChanged(it)) },
                isError = state.value.isInvalidCredentialsError || state.value.isEmailError,
                hint = stringResource(id = R.string.email_address),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { focusRequesterPassword.requestFocus() }
                ),
                errorMessages = emailErrors
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DemoInputFieldPassword(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .focusRequester(focusRequesterPassword),
                    value = state.value.password,
                    onValueChange = { onIntent(AuthViewModel.UiIntent.PasswordChanged(it)) },
                    isError = state.value.isInvalidCredentialsError || state.value.isPasswordValid == false,
                    hint = stringResource(id = R.string.password),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                )
                AnimatedVisibility(visible = state.value.password.isNotBlank()) {
                    PasswordValidationHint(
                        satisfiedRules = state.value.satisfiedPasswordRules,
                    )
                }
            }

            AnimatedVisibility(
                visible = state.value.isInvalidCredentialsError,
            ) {
                Surface(
                    modifier = Modifier.padding(bottom = 12.dp),
                    shape = DemoTheme.shapes.xs,
                    color = DemoTheme.colors.errorBackground,
                    border = BorderStroke(width = 1.dp, color = DemoTheme.colors.errorBorder)
                ) {
                    Text(
                        text = stringResource(R.string.error_invalid_credentials),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        style = DemoTheme.typography.P4XL.copy(color = DemoTheme.colors.error),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        DemoButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            text = stringResource(R.string.log_in),
            onClick = { onIntent(AuthViewModel.UiIntent.SignInClicked) },
            buttonColors = DemoButtonColors.primary(),
            shape = DemoTheme.shapes.xxl,
            isLoading = state.value.isSignInLoading,
            isEnabled = state.value.canAuthenticate
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                thickness = 1.dp,
                color = DemoTheme.colors.uiBorder
            )
            Text(
                text = stringResource(id = R.string.or),
                style = DemoTheme.typography.H6XL,
                color = DemoTheme.colors.textPrimary
            )
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                thickness = 1.dp,
                color = DemoTheme.colors.uiBorder
            )
        }
        DemoButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            text = stringResource(R.string.register),
            onClick = { onIntent(AuthViewModel.UiIntent.SignUpClicked) },
            buttonColors = DemoButtonColors.secondary(),
            shape = DemoTheme.shapes.xxl,
            isLoading = state.value.isRegisterLoading,
            isEnabled = state.value.canAuthenticate
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showBackground = true, name = "Auth Screen Content - Default")
@Composable
fun AuthScreenContentDefaultPreview() {
    DemoTheme {
        val dummyState = remember {
            mutableStateOf(
                AuthViewModel.UiState(
                    email = "user@example.com",
                    password = "Password123!",
                    isEmailError = false,
                    isInvalidCredentialsError = false,
                    isPasswordValid = true,
                    satisfiedPasswordRules = persistentSetOf(
                        PasswordValidationRule.MINIMUM_LENGTH,
                        PasswordValidationRule.SPECIAL_CHARACTER,
                        PasswordValidationRule.LOWERCASE_LETTER,
                        PasswordValidationRule.UPPERCASE_LETTER,
                        PasswordValidationRule.DIGIT,
                    ),
                    isSignInLoading = false,
                    isRegisterLoading = false,
                    canAuthenticate = true,
                )
            )
        }
        AuthScreenContent(state = dummyState, onIntent = {})
    }
}

@Preview(showBackground = true, name = "Auth Screen Content - Error")
@Composable
fun AuthScreenContentErrorPreview() {
    DemoTheme {
        val dummyState = remember {
            mutableStateOf(
                AuthViewModel.UiState(
                    email = "wrong-email",
                    password = "1",
                    isEmailError = true,
                    isInvalidCredentialsError = true,
                    isPasswordValid = false,
                    satisfiedPasswordRules = persistentSetOf(PasswordValidationRule.DIGIT),
                    isSignInLoading = false,
                    isRegisterLoading = false,
                    canAuthenticate = false,
                )
            )
        }
        AuthScreenContent(state = dummyState, onIntent = {})
    }
}