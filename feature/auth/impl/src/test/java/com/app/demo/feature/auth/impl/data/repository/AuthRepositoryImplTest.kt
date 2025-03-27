package com.app.demo.feature.auth.impl.data.repository

import com.app.demo.feature.auth.impl.data.api.AuthWebService
import com.app.demo.feature.auth.impl.data.dto.UserDto
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AuthRepositoryImplTest {

    private lateinit var authWebService: AuthWebService
    private lateinit var authRepository: AuthRepositoryImpl
    private lateinit var testDispatcher: CoroutineDispatcher

    @BeforeEach
    fun setUp() {
        authWebService = mockk()
        testDispatcher = UnconfinedTestDispatcher()
        authRepository = AuthRepositoryImpl(testDispatcher, authWebService)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `when login success then return user model`() = runTest {
        // Given
        val email = "user@example.com"
        val password = "password123"
        val userDto = UserDto(id = 1, email = email, username = "user1")
        coEvery { authWebService.login(email, password) } returns Result.success(userDto)

        // When
        val result = authRepository.login(email, password)

        // Then
        assertThat(result.isSuccess).isTrue()
        val user = result.getOrNull()!!
        assertThat(user.id).isEqualTo(userDto.id.toString())
        assertThat(user.email).isEqualTo(userDto.email)
        assertThat(user.username).isEqualTo(userDto.username)
        coVerify(exactly = 1) { authWebService.login(email, password) }
    }

    @Test
    fun `when login failure then return failure`() = runTest {
        // Given
        val email = "user@example.com"
        val password = "password123"
        val exception = Exception("Login failed")
        coEvery { authWebService.login(email, password) } returns Result.failure(exception)

        // When
        val result = authRepository.login(email, password)

        // Then
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isEqualTo(exception)
        coVerify(exactly = 1) { authWebService.login(email, password) }
    }

    @Test
    fun `when register success then return user model`() = runTest {
        // Given
        val email = "newuser@example.com"
        val password = "newpassword"
        val userDto = UserDto(id = 2, email = email, username = "newuser")
        coEvery { authWebService.register(email, password) } returns Result.success(userDto)

        // When
        val result = authRepository.register(email, password)

        // Then
        assertThat(result.isSuccess).isTrue()
        val user = result.getOrNull()!!
        assertThat(user.id).isEqualTo(userDto.id.toString())
        assertThat(user.email).isEqualTo(userDto.email)
        assertThat(user.username).isEqualTo(userDto.username)
        coVerify(exactly = 1) { authWebService.register(email, password) }
    }

    @Test
    fun `when register failure then return failure`() = runTest {
        // Given
        val email = "newuser@example.com"
        val password = "newpassword"
        val exception = Exception("Registration failed")
        coEvery { authWebService.register(email, password) } returns Result.failure(exception)

        // When
        val result = authRepository.register(email, password)

        // Then
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isEqualTo(exception)
        coVerify(exactly = 1) { authWebService.register(email, password) }
    }
}