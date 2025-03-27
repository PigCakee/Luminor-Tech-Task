package com.app.demo.feature.auth.impl.data.repository

import com.app.demo.di.qualifier.IoDispatcher
import com.app.demo.feature.auth.impl.data.mapper.toDbo
import com.app.demo.feature.auth.impl.data.mapper.toModel
import com.app.demo.feature.auth.shared.domain.model.User
import com.app.demo.feature.auth.shared.domain.repository.UserRepository
import com.app.demo.storage.shared.dao.UserDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Concrete implementation of the [UserRepository] interface. Handles interactions with the user data source (database).
 */
@Singleton
class UserRepositoryImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val userDao: com.app.demo.storage.shared.dao.UserDao
) : UserRepository {

    /**
     * Saves a user to the local database.
     * This function inserts or updates a user record in the database based on the provided [User] object.
     */
    override suspend fun saveUser(user: User) {
        withContext(ioDispatcher) {
            userDao.insertUser(user.toDbo())
        }
    }

    /**
     * Retrieves a user from the database by their unique identifier.
     */
    override suspend fun getUserById(id: String): User? {
        return withContext(ioDispatcher) {
            userDao.getUserById(id)?.toModel()
        }
    }
}