package com.app.demo.feature.auth.impl.domain.usecase

import com.app.demo.feature.auth.shared.domain.model.User
import com.app.demo.feature.auth.shared.domain.repository.UserRepository
import com.app.demo.storage.shared.datastore.loggedInUser.LoggedInUserProvider
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetLoggedInUserUseCaseImplTest {

    private lateinit var loggedInUserProvider: LoggedInUserProvider
    private lateinit var userRepository: UserRepository
    private lateinit var getLoggedInUserUseCaseImpl: GetLoggedInUserUseCaseImpl
    private lateinit var testDispatcher: CoroutineDispatcher

    @BeforeEach
    fun setUp() {
        loggedInUserProvider = mockk()
        userRepository = mockk()
        testDispatcher = UnconfinedTestDispatcher()
        getLoggedInUserUseCaseImpl = GetLoggedInUserUseCaseImpl(testDispatcher, userRepository, loggedInUserProvider)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `when user is logged in then return success result with user`() = runTest {
        // Given
        val userId = "123"
        val user = User(id = userId, email = "user@example.com", username = "username")
        coEvery { loggedInUserProvider.getLoggedInUserId() } returns userId
        coEvery { userRepository.getUserById(userId) } returns user

        // When
        val result = getLoggedInUserUseCaseImpl.invoke()

        // Then
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(user)
    }

    @Test
    fun `when no user id found in datastore then return failure result`() = runTest {
        // Given
        coEvery { loggedInUserProvider.getLoggedInUserId() } returns null

        // When
        val result = getLoggedInUserUseCaseImpl.invoke()

        // Then
        assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `when user repository returns null then return failure result`() = runTest {
        // Given
        val userId = "123"
        coEvery { loggedInUserProvider.getLoggedInUserId() } returns userId
        coEvery { userRepository.getUserById(userId) } returns null

        // When
        val result = getLoggedInUserUseCaseImpl.invoke()

        // Then
        assertThat(result.isFailure).isTrue()
    }
}