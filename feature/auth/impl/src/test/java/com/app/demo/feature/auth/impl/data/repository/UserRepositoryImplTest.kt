package com.app.demo.feature.auth.impl.data.repository

import com.app.demo.feature.auth.impl.data.mapper.toDbo
import com.app.demo.feature.auth.impl.data.mapper.toModel
import com.app.demo.feature.auth.shared.domain.model.User
import com.app.demo.storage.shared.dao.UserDao
import com.app.demo.storage.shared.model.UserDbo
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

class UserRepositoryImplTest {

    private lateinit var userDao: com.app.demo.storage.shared.dao.UserDao
    private lateinit var userRepository: UserRepositoryImpl
    private lateinit var testDispatcher: CoroutineDispatcher

    @BeforeEach
    fun setUp() {
        userDao = mockk()
        testDispatcher = UnconfinedTestDispatcher()
        userRepository = UserRepositoryImpl(testDispatcher, userDao)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `when save user then call dao insertUser`() = runTest {
        // Given
        val user = User(id = "123", email = "user@example.com", username = "username")
        val userDbo = user.toDbo()
        coEvery { userDao.insertUser(userDbo) } returns Unit

        // When
        userRepository.saveUser(user)

        // Then
        coVerify(exactly = 1) { userDao.insertUser(userDbo) }
    }

    @Test
    fun `when get user by id found then return user model`() = runTest {
        // Given
        val id = "123"
        val userDbo = com.app.demo.storage.shared.model.UserDbo(
            id = id,
            email = "user@example.com",
            username = "username"
        )
        coEvery { userDao.getUserById(id) } returns userDbo

        // When
        val result = userRepository.getUserById(id)

        // Then
        val expectedUser = userDbo.toModel()
        assertThat(result).isEqualTo(expectedUser)
        coVerify(exactly = 1) { userDao.getUserById(id) }
    }

    @Test
    fun `when get user by id not found then return null`() = runTest {
        // Given
        val id = "123"
        coEvery { userDao.getUserById(id) } returns null

        // When
        val result = userRepository.getUserById(id)

        // Then
        assertThat(result).isNull()
        coVerify(exactly = 1) { userDao.getUserById(id) }
    }
}