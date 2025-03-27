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

class RegisterUseCaseImplTest {

    private lateinit var authRepository: AuthRepository
    private lateinit var saveLoggedInUserUseCase: SaveLoggedInUserUseCase
    private lateinit var registerUseCase: RegisterUseCaseImpl

    @BeforeEach
    fun setUp() {
        authRepository = mockk()
        saveLoggedInUserUseCase = mockk()
        registerUseCase = RegisterUseCaseImpl(authRepository, saveLoggedInUserUseCase)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `when register success then return user and call saveLoggedInUserUseCase`() = runTest {
        // Given
        val email = "newuser@example.com"
        val password = "newpassword"
        val user = User(id = "2", email = email, username = "newuser")
        coEvery { authRepository.register(email, password) } returns Result.success(user)
        coEvery { saveLoggedInUserUseCase.invoke(user) } returns Unit

        // When
        val result = registerUseCase.invoke(email, password)

        // Then
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(user)
        coVerify(exactly = 1) { saveLoggedInUserUseCase.invoke(user) }
    }

    @Test
    fun `when register failure then return failure and do not call saveLoggedInUserUseCase`() = runTest {
        // Given
        val email = "newuser@example.com"
        val password = "newpassword"
        val exception = Exception("Registration failed")
        coEvery { authRepository.register(email, password) } returns Result.failure(exception)

        // When
        val result = registerUseCase.invoke(email, password)

        // Then
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isEqualTo(exception)
        coVerify(exactly = 0) { saveLoggedInUserUseCase.invoke(any()) }
    }
}