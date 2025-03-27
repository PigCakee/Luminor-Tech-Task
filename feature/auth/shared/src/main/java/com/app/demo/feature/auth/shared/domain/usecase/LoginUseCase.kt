package com.app.demo.feature.auth.shared.domain.usecase

import com.app.demo.feature.auth.shared.domain.model.User

/**
 * A use case responsible for logging in a user.
 */
interface LoginUseCase {
    suspend operator fun invoke(email: String, password: String): Result<User>
}