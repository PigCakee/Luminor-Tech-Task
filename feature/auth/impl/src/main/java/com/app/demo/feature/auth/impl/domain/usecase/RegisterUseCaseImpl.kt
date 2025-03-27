package com.app.demo.feature.auth.impl.domain.usecase

import com.app.demo.feature.auth.shared.domain.model.User
import com.app.demo.feature.auth.shared.domain.repository.AuthRepository
import com.app.demo.feature.auth.shared.domain.usecase.RegisterUseCase
import com.app.demo.feature.auth.shared.domain.usecase.SaveLoggedInUserUseCase
import javax.inject.Inject

/**
 * Implementation of the [RegisterUseCase] interface.
 *
 * This class handles the user registration process, including interacting with the [AuthRepository]
 * to register the user and using the [SaveLoggedInUserUseCase] to persist the logged-in user information
 * upon successful registration.
 */
class RegisterUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository,
    private val saveLoggedInUserUseCase: SaveLoggedInUserUseCase,
) : RegisterUseCase {
    override suspend fun invoke(email: String, password: String): Result<User> {
        return authRepository.register(email = email, password = password).fold(
            onSuccess = { user ->
                saveLoggedInUserUseCase.invoke(user)
                Result.success(user)
            },
            onFailure = { exception -> Result.failure(exception) }
        )
    }
}