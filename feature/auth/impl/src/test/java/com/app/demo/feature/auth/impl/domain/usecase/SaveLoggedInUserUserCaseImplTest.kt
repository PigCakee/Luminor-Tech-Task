package com.app.demo.feature.auth.impl.domain.usecase

import com.app.demo.feature.auth.shared.domain.model.User
import com.app.demo.feature.auth.shared.domain.repository.UserRepository
import com.app.demo.feature.auth.shared.domain.usecase.SaveLoggedInUserUseCase
import com.app.demo.storage.shared.datastore.loggedInUser.LoggedInUserManager
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SaveLoggedInUserUseCaseImplTest {

    private lateinit var loggedInUserManager: LoggedInUserManager
    private lateinit var userRepository: UserRepository
    private lateinit var saveLoggedInUserUseCase: SaveLoggedInUserUseCase

    @BeforeEach
    fun setUp() {
        loggedInUserManager = mockk()
        userRepository = mockk()
        saveLoggedInUserUseCase = SaveLoggedInUserUseCaseImpl(loggedInUserManager, userRepository)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `when invoke then call saveUser and saveLoggedInUserId`() = runTest {
        // Given
        val user = User(id = "123", email = "user@example.com", username = "username")
        coEvery { userRepository.saveUser(user) } returns Unit
        coEvery { loggedInUserManager.saveLoggedInUserId(user.id) } returns Unit

        // When
        saveLoggedInUserUseCase.invoke(user)

        // Then
        coVerify(exactly = 1) { userRepository.saveUser(user) }
        coVerify(exactly = 1) { loggedInUserManager.saveLoggedInUserId(user.id) }
    }
}