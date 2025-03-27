package com.app.demo.feature.auth.shared.domain.repository

import com.app.demo.feature.auth.shared.domain.model.User

/**
 * An interface for handling user authentication and registration.
 */
interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun register(email: String, password: String): Result<User>
}
