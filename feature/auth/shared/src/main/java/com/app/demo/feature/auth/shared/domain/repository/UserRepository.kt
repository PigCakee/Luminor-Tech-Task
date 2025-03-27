package com.app.demo.feature.auth.shared.domain.repository

import com.app.demo.feature.auth.shared.domain.model.User

/**
 * Repository interface for managing user data.
 */
interface UserRepository {

    suspend fun saveUser(user: User)

    suspend fun getUserById(id: String): User?
}