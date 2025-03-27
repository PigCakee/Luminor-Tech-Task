package com.app.demo.feature.auth.impl.domain.usecase

import com.app.demo.feature.auth.shared.domain.model.User
import com.app.demo.feature.auth.shared.domain.repository.AuthRepository
import com.app.demo.feature.auth.shared.domain.usecase.LoginUseCase
import com.app.demo.feature.auth.shared.domain.usecase.SaveLoggedInUserUseCase
import javax.inject.Inject

/**
 * This class handles user login by interacting with the [AuthRepository] and [SaveLoggedInUserUseCase].
 * It attempts to log in a user with the provided email and password. If successful, it saves the
 * logged-in user's information using [SaveLoggedInUserUseCase] and returns a success result containing
 * the user's details. If login fails, it returns a failure result with the corresponding exception.
 */
class LoginUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository,
    private val saveLoggedInUserUseCase: SaveLoggedInUserUseCase
) : LoginUseCase {
    override suspend operator fun invoke(email: String, password: String): Result<User> {
        return authRepository.login(email = email, password = password).fold(
            onSuccess = { user ->
                saveLoggedInUserUseCase.invoke(user)
                Result.success(user)
            },
            onFailure = { exception -> Result.failure(exception) }
        )
    }
}