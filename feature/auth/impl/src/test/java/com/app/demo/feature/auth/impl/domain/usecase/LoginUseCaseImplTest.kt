package com.app.demo.feature.auth.impl.domain.usecase

import com.app.demo.feature.auth.shared.domain.model.User
import com.app.demo.feature.auth.shared.domain.repository.AuthRepository
import com.app.demo.feature.auth.shared.domain.usecase.SaveLoggedInUserUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LoginUseCaseImplTest {

    private lateinit var authRepository: AuthRepository
    private lateinit var saveLoggedInUserUseCase: SaveLoggedInUserUseCase
    private lateinit var loginUseCase: LoginUseCaseImpl

    @BeforeEach
    fun setUp() {
        authRepository = mockk()
        saveLoggedInUserUseCase = mockk()
        loginUseCase = LoginUseCaseImpl(authRepository, saveLoggedInUserUseCase)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `when login success then return user and call saveLoggedInUserUseCase`() = runTest {
        // Given
        val email = "user@example.com"
        val password = "password123"
        val user = User(id = "1", email = email, username = "username")
        coEvery { authRepository.login(email, password) } returns Result.success(user)
        coEvery { saveLoggedInUserUseCase.invoke(user) } returns Unit

        // When
        val result = loginUseCase.invoke(email, password)

        // Then
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(user)
        coVerify(exactly = 1) { saveLoggedInUserUseCase.invoke(user) }
    }

    @Test
    fun `when login failure then return failure and do not call saveLoggedInUserUseCase`() = runTest {
        // Given
        val email = "user@example.com"
        val password = "password123"
        val exception = Exception("Login failed")
        coEvery { authRepository.login(email, password) } returns Result.failure(exception)

        // When
        val result = loginUseCase.invoke(email, password)

        // Then
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isEqualTo(exception)
        coVerify(exactly = 0) { saveLoggedInUserUseCase.invoke(any()) }
    }
}