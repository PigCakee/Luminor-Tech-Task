package com.app.demo.feature.auth.ui

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.test.platform.app.InstrumentationRegistry
import com.app.demo.feature.auth.ui.screen.AuthScreenContent
import com.app.demo.feature.auth.ui.screen.AuthViewModel
import com.app.demo.feature.auth.ui.screen.AuthViewModel.UiIntent
import com.app.demo.ui.theme.DemoTheme
import kotlinx.collections.immutable.persistentSetOf
import org.junit.Rule
import org.junit.Test

class AuthScreenContentUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun authScreenContent_defaultState_showsCorrectUIElements() {
        // Given a dummy valid state.
        val dummyState = mutableStateOf(
            AuthViewModel.UiState(
                email = "user@example.com",
                password = "Password123!",
                isEmailError = false,
                isInvalidCredentialsError = false,
                isPasswordValid = true,
                satisfiedPasswordRules = persistentSetOf(
                    // Assume all password rules are satisfied.
                ),
                isSignInLoading = false,
                isRegisterLoading = false,
                canAuthenticate = true,
            )
        )
        composeTestRule.setContent {
            DemoTheme {
                AuthScreenContent(state = dummyState, onIntent = {})
            }
        }
        // Then "Authentication" title is displayed.
        composeTestRule.onNodeWithText("Authentication").assertIsDisplayed()
        // And the email is pre-filled.
        composeTestRule.onNodeWithText("user@example.com").assertIsDisplayed()
        // And the Log in and Register buttons are displayed.
        composeTestRule.onNodeWithText("Log in").assertIsDisplayed()
        composeTestRule.onNodeWithText("Register").performScrollTo().assertIsDisplayed()
    }

    @Test
    fun authScreenContent_errorState_showsInvalidCredentialsError() {
        // Given a dummy error state.
        val dummyState = mutableStateOf(
            AuthViewModel.UiState(
                email = "wrong-email",
                password = "1",
                isEmailError = true,
                isInvalidCredentialsError = true,
                isPasswordValid = false,
                satisfiedPasswordRules = persistentSetOf(), // no rules satisfied
                isSignInLoading = false,
                isRegisterLoading = false,
                canAuthenticate = false,
            )
        )
        composeTestRule.setContent {
            DemoTheme {
                AuthScreenContent(state = dummyState, onIntent = {})
            }
        }
        // Then the error message "Invalid credentials" is shown.
        // Retrieve the expected string from resources.
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val expectedError = context.getString(R.string.error_invalid_credentials)
        composeTestRule.onNodeWithText(expectedError).assertIsDisplayed()
    }

    @Test
    fun authScreenContent_clickLoginButton_triggersSignInIntent() {
        var signInClicked = false
        val dummyState = mutableStateOf(
            AuthViewModel.UiState(
                email = "user@example.com",
                password = "Password123!",
                isEmailError = false,
                isInvalidCredentialsError = false,
                isPasswordValid = true,
                satisfiedPasswordRules = persistentSetOf(),
                isSignInLoading = false,
                isRegisterLoading = false,
                canAuthenticate = true,
            )
        )
        composeTestRule.setContent {
            DemoTheme {
                AuthScreenContent(
                    state = dummyState,
                    onIntent = { intent ->
                        if (intent is UiIntent.SignInClicked) {
                            signInClicked = true
                        }
                    }
                )
            }
        }
        // When clicking the "Log in" button.
        composeTestRule.onNodeWithText("Log in").performClick()
        // Then the SignInClicked intent is triggered.
        composeTestRule.runOnIdle {
            assert(signInClicked)
        }
    }

    @Test
    fun authScreenContent_clickRegisterButton_triggersSignUpIntent() {
        var signUpClicked = false
        val dummyState = mutableStateOf(
            AuthViewModel.UiState(
                email = "user@example.com",
                password = "Password123!",
                isEmailError = false,
                isInvalidCredentialsError = false,
                isPasswordValid = true,
                satisfiedPasswordRules = persistentSetOf(),
                isSignInLoading = false,
                isRegisterLoading = false,
                canAuthenticate = true,
            )
        )
        composeTestRule.setContent {
            DemoTheme {
                AuthScreenContent(
                    state = dummyState,
                    onIntent = { intent ->
                        if (intent is UiIntent.SignUpClicked) {
                            signUpClicked = true
                        }
                    }
                )
            }
        }
        // When clicking the "Register" button.
        composeTestRule.onNodeWithText("Register").performScrollTo().performClick()
        // Then the SignUpClicked intent is triggered.
        composeTestRule.runOnIdle {
            assert(signUpClicked)
        }
    }
}