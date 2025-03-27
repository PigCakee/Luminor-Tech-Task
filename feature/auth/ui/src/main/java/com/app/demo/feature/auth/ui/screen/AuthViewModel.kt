package com.app.demo.feature.auth.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.demo.feature.auth.shared.domain.model.error.InvalidCredentialsException
import com.app.demo.feature.auth.shared.domain.model.error.UserAlreadyExistsException
import com.app.demo.feature.auth.shared.domain.usecase.LoginUseCase
import com.app.demo.feature.auth.shared.domain.usecase.RegisterUseCase
import com.app.demo.feature.auth.ui.R
import com.app.demo.navigation.shared.navigation.AuthNavGraph
import com.app.demo.navigation.shared.navigation.NavigationCommand
import com.app.demo.navigation.shared.navigation.NavigationManager
import com.app.demo.navigation.shared.navigation.RootNavGraph
import com.app.demo.navigation.shared.navigation.showGenericErrorToast
import com.app.demo.navigation.shared.navigation.showToast
import com.app.demo.ui.model.UiText
import com.app.demo.utility.validator.EmailFieldValidator
import com.app.demo.utility.validator.PasswordValidationRule
import com.app.demo.utility.validator.SignUpPasswordFieldValidator
import com.app.demo.utility.validator.SignUpPasswordFieldValidator.VALID_PASSWORD_RULE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toPersistentSet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val navigationManager: NavigationManager
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    fun acceptIntent(intent: UiIntent) {
        when (intent) {
            is UiIntent.EmailChanged -> onEmailChanged(intent.email)
            is UiIntent.PasswordChanged -> onPasswordChanged(intent.password)
            is UiIntent.SignInClicked -> onSignInClicked()
            is UiIntent.SignUpClicked -> onSignUpClicked()
        }
    }

    private fun onSignInClicked() {
        val email = state.value.email
        val password = state.value.password
        if (canAuthenticate(email = email, password = password).not()) {
            return
        }
        _state.update { state -> state.copy(isSignInLoading = true) }
        viewModelScope.launch {
            loginUseCase.invoke(email = email, password = password).fold(
                onSuccess = {
                    _state.update { state -> state.copy(isSignInLoading = false) }
                    navigateToHome()
                },
                onFailure = { error ->
                    if (error is InvalidCredentialsException) {
                        _state.update { state ->
                            state.copy(
                                isSignInLoading = false,
                                isInvalidCredentialsError = true,
                            )
                        }
                    } else {
                        _state.update { state ->
                            state.copy(
                                isSignInLoading = false,
                            )
                        }
                        navigationManager.showGenericErrorToast()
                    }
                }
            )
        }
    }

    private fun onSignUpClicked() {
        val email = state.value.email
        val password = state.value.password
        if (canAuthenticate(email = email, password = password).not()) {
            return
        }
        _state.update { state -> state.copy(isRegisterLoading = true) }
        viewModelScope.launch {
            registerUseCase.invoke(email = email, password = password).fold(
                onSuccess = {
                    _state.update { state -> state.copy(isRegisterLoading = false) }
                    navigateToHome()
                },
                onFailure = { error ->
                    if (error is UserAlreadyExistsException) {
                        _state.update { state ->
                            state.copy(
                                isRegisterLoading = false,
                                isUserExistsError = true
                            )
                        }
                        navigationManager.showToast(UiText.Id(R.string.error_user_exists))
                    } else {
                        _state.update { state ->
                            state.copy(isRegisterLoading = false)
                        }
                        navigationManager.showGenericErrorToast()
                    }
                }
            )
        }
    }

    private fun navigateToHome() {
        navigationManager.navigate(
            NavigationCommand.CleanForward(
                destination = RootNavGraph,
                popDestination = AuthNavGraph,
                inclusive = true
            )
        )
    }

    private fun onEmailChanged(email: String) {
        val shortenedEmail = EmailFieldValidator.takeAllowedLength(email)
        val isEmailValid = isEmailValid(shortenedEmail)
        val password = state.value.password
        _state.update { state ->
            state.copy(
                email = shortenedEmail,
                isEmailError = !isEmailValid,
                isInvalidCredentialsError = false,
                canAuthenticate = canAuthenticate(email, password),
            )
        }
    }

    private fun onPasswordChanged(password: String) {
        val satisfiedPasswordRules = SignUpPasswordFieldValidator.getSatisfiedValidationRules(
            password = password
        ).toPersistentSet()
        val isPasswordValid = satisfiedPasswordRules.size == VALID_PASSWORD_RULE_SIZE
        _state.update { state ->
            state.copy(
                password = password,
                isInvalidCredentialsError = false,
                canAuthenticate = canAuthenticate(state.email, password),
                satisfiedPasswordRules = satisfiedPasswordRules,
                isPasswordValid = isPasswordValid
            )
        }
    }

    private fun canAuthenticate(email: String, password: String): Boolean {
        val shortenedEmail = EmailFieldValidator.takeAllowedLength(email)
        val passwordRules = SignUpPasswordFieldValidator.getSatisfiedValidationRules(password)
        return isEmailValid(shortenedEmail) && passwordRules.size == VALID_PASSWORD_RULE_SIZE
    }

    private fun isEmailValid(email: String): Boolean {
        return email.isNotEmpty() && EmailFieldValidator.validate(email)
    }

    sealed interface UiIntent {
        data class EmailChanged(val email: String) : UiIntent
        data class PasswordChanged(val password: String) : UiIntent
        data object SignInClicked : UiIntent
        data object SignUpClicked : UiIntent
    }

    data class UiState(
        val email: String = "",
        val isEmailError: Boolean = false,
        val isInvalidCredentialsError: Boolean = false,
        val isUserExistsError: Boolean = false,
        val password: String = "",
        val isSignInLoading: Boolean = false,
        val isRegisterLoading: Boolean = false,
        val canAuthenticate: Boolean = false,
        val isPasswordValid: Boolean? = null,
        val satisfiedPasswordRules: PersistentSet<PasswordValidationRule> = persistentSetOf(),
    )
}