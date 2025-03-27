package com.app.demo.feature.auth.ui.viewmodel

import app.cash.turbine.test
import com.app.demo.feature.auth.shared.domain.model.User
import com.app.demo.feature.auth.shared.domain.model.error.InvalidCredentialsException
import com.app.demo.feature.auth.shared.domain.model.error.UserAlreadyExistsException
import com.app.demo.feature.auth.shared.domain.usecase.LoginUseCase
import com.app.demo.feature.auth.shared.domain.usecase.RegisterUseCase
import com.app.demo.feature.auth.ui.R
import com.app.demo.feature.auth.ui.screen.AuthViewModel
import com.app.demo.navigation.shared.navigation.AuthNavGraph
import com.app.demo.navigation.shared.navigation.NavigationCommand
import com.app.demo.navigation.shared.navigation.NavigationManager
import com.app.demo.navigation.shared.navigation.RootNavGraph
import com.app.demo.navigation.shared.navigation.showGenericErrorToast
import com.app.demo.navigation.shared.navigation.showToast
import com.app.demo.ui.model.UiText
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AuthViewModelTest {

    private lateinit var loginUseCase: LoginUseCase
    private lateinit var registerUseCase: RegisterUseCase
    private lateinit var navigationManager: NavigationManager
    private lateinit var viewModel: AuthViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        loginUseCase = mockk()
        registerUseCase = mockk()
        navigationManager = mockk(relaxed = true)
        viewModel = AuthViewModel(loginUseCase, registerUseCase, navigationManager)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `when email changed then state is updated with valid email`() = runTest {
        viewModel.state.test {
            // Given initial state with empty email
            val initialState = awaitItem()
            // When sending a valid email
            viewModel.acceptIntent(AuthViewModel.UiIntent.EmailChanged("user@example.com"))
            // Then state should update with the new email and no email error.
            val updatedState = awaitItem()
            assertThat(updatedState.email).isEqualTo("user@example.com")
            assertThat(updatedState.isEmailError).isFalse()
            // Since password is still empty, canAuthenticate remains false.
            assertThat(updatedState.canAuthenticate).isFalse()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when password changed then state is updated with valid password and can authenticate`() =
        runTest {
            // Given a valid email is already set.
            viewModel.acceptIntent(AuthViewModel.UiIntent.EmailChanged("user@example.com"))
            viewModel.state.test {
                // Get state after email update.
                val stateAfterEmail = awaitItem()
                // When sending a valid password (assumed to satisfy all rules)
                viewModel.acceptIntent(AuthViewModel.UiIntent.PasswordChanged("Password123!"))
                // Then state should update with the new password, valid password flag, and canAuthenticate true.
                val updatedState = awaitItem()
                assertThat(updatedState.password).isEqualTo("Password123!")
                assertThat(updatedState.isPasswordValid).isTrue()
                assertThat(updatedState.canAuthenticate).isTrue()
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when sign in clicked and login succeeds then state is updated and navigates to home`() =
        runTest {
            // Given valid email and password are set.
            viewModel.acceptIntent(AuthViewModel.UiIntent.EmailChanged("user@example.com"))
            viewModel.acceptIntent(AuthViewModel.UiIntent.PasswordChanged("Password123!"))
            val dummyUser = User(id = "1", email = "user@example.com", username = "username")
            coEvery {
                loginUseCase.invoke(
                    "user@example.com",
                    "Password123!"
                )
            } returns Result.success(dummyUser)

            viewModel.state.test {
                // When sign in is clicked.
                awaitItem()
                viewModel.acceptIntent(AuthViewModel.UiIntent.SignInClicked)
                // Expect state update: first loading true then loading false.
                val loadingState = awaitItem()
                val finalState = awaitItem()
                assertThat(finalState.isSignInLoading).isFalse()
                cancelAndIgnoreRemainingEvents()
            }
            coVerify(exactly = 1) {
                navigationManager.navigate(
                    NavigationCommand.CleanForward(
                        destination = RootNavGraph,
                        popDestination = AuthNavGraph,
                        inclusive = true
                    )
                )
            }
        }

    @Test
    fun `when sign in clicked with invalid credentials then state is updated with error`() =
        runTest {
            // Given valid email and password.
            viewModel.acceptIntent(AuthViewModel.UiIntent.EmailChanged("user@example.com"))
            viewModel.acceptIntent(AuthViewModel.UiIntent.PasswordChanged("Password123!"))
            val exception = InvalidCredentialsException()
            coEvery {
                loginUseCase.invoke(
                    "user@example.com",
                    "Password123!"
                )
            } returns Result.failure(exception)

            viewModel.state.test {
                awaitItem()
                viewModel.acceptIntent(AuthViewModel.UiIntent.SignInClicked)
                // Expect state update: loading true then loading false with error flag.
                val loadingState = awaitItem()
                val errorState = awaitItem()
                assertThat(errorState.isSignInLoading).isFalse()
                assertThat(errorState.isInvalidCredentialsError).isTrue()
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when sign up clicked and registration succeeds then state is updated and navigates to home`() =
        runTest {
            // Given valid email and password.
            viewModel.acceptIntent(AuthViewModel.UiIntent.EmailChanged("user@example.com"))
            viewModel.acceptIntent(AuthViewModel.UiIntent.PasswordChanged("Password123!"))
            val dummyUser = User(id = "1", email = "user@example.com", username = "username")
            coEvery {
                registerUseCase.invoke(
                    "user@example.com",
                    "Password123!"
                )
            } returns Result.success(dummyUser)

            viewModel.state.test {
                awaitItem()
                viewModel.acceptIntent(AuthViewModel.UiIntent.SignUpClicked)
                // Expect state update: register loading true then register loading false.
                val loadingState = awaitItem()
                val finalState = awaitItem()
                assertThat(finalState.isRegisterLoading).isFalse()
                cancelAndIgnoreRemainingEvents()
            }
            coVerify(exactly = 1) {
                navigationManager.navigate(
                    NavigationCommand.CleanForward(
                        destination = RootNavGraph,
                        popDestination = AuthNavGraph,
                        inclusive = true
                    )
                )
            }
        }

    @Test
    fun `when sign up clicked with user already exists then state is updated with error and shows toast`() =
        runTest {
            // Given valid email and password.
            viewModel.acceptIntent(AuthViewModel.UiIntent.EmailChanged("user@example.com"))
            viewModel.acceptIntent(AuthViewModel.UiIntent.PasswordChanged("Password123!"))
            val exception = UserAlreadyExistsException()
            coEvery {
                registerUseCase.invoke(
                    "user@example.com",
                    "Password123!"
                )
            } returns Result.failure(exception)

            viewModel.state.test {
                awaitItem()
                viewModel.acceptIntent(AuthViewModel.UiIntent.SignUpClicked)
                // Expect state update: register loading true then loading false with user exists error.
                val loadingState = awaitItem()
                val errorState = awaitItem()
                assertThat(errorState.isRegisterLoading).isFalse()
                assertThat(errorState.isUserExistsError).isTrue()
                cancelAndIgnoreRemainingEvents()
            }
            coVerify(exactly = 1) {
                navigationManager.showToast(UiText.Id(R.string.error_user_exists))
            }
        }

    @Test
    fun `when sign up clicked with generic error then state is updated and shows generic error toast`() =
        runTest {
            // Given valid email and password.
            viewModel.acceptIntent(AuthViewModel.UiIntent.EmailChanged("user@example.com"))
            viewModel.acceptIntent(AuthViewModel.UiIntent.PasswordChanged("Password123!"))
            val exception = Exception("Generic error")
            coEvery {
                registerUseCase.invoke(
                    "user@example.com",
                    "Password123!"
                )
            } returns Result.failure(exception)

            viewModel.state.test {
                awaitItem()
                viewModel.acceptIntent(AuthViewModel.UiIntent.SignUpClicked)
                // Expect state update: register loading true then loading false.
                val loadingState = awaitItem()
                val finalState = awaitItem()
                assertThat(finalState.isRegisterLoading).isFalse()
                cancelAndIgnoreRemainingEvents()
            }
            coVerify(exactly = 1) { navigationManager.showGenericErrorToast() }
        }

    @Test
    fun `when email changed with invalid email then state is updated with email error`() = runTest {
        viewModel.state.test {
            // Given initial state.
            val initialState = awaitItem()
            // When sending an invalid email.
            viewModel.acceptIntent(AuthViewModel.UiIntent.EmailChanged("invalid-email"))
            // Then state should update with the new email, set email error to true, and disable authentication.
            val updatedState = awaitItem()
            assertThat(updatedState.email).isEqualTo("invalid-email")
            assertThat(updatedState.isEmailError).isTrue()
            assertThat(updatedState.canAuthenticate).isFalse()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when password changed with invalid password then state is updated with invalid password and cannot authenticate`() =
        runTest {
            // Given a valid email is already set.
            viewModel.acceptIntent(AuthViewModel.UiIntent.EmailChanged("user@example.com"))
            viewModel.state.test {
                // Get state after email update.
                val stateAfterEmail = awaitItem()
                // When sending an invalid password (assume "pass" does not satisfy all rules).
                viewModel.acceptIntent(AuthViewModel.UiIntent.PasswordChanged("pass"))
                // Then state should update with the new password, mark password as invalid, and disable authentication.
                val updatedState = awaitItem()
                assertThat(updatedState.password).isEqualTo("pass")
                assertThat(updatedState.isPasswordValid).isFalse()
                assertThat(updatedState.canAuthenticate).isFalse()
                cancelAndIgnoreRemainingEvents()
            }
        }
}