package com.app.demo.feature.auth.impl.data.repository

import com.app.demo.di.qualifier.IoDispatcher
import com.app.demo.feature.auth.impl.data.api.AuthWebService
import com.app.demo.feature.auth.impl.data.mapper.toModel
import com.app.demo.feature.auth.shared.domain.model.User
import com.app.demo.feature.auth.shared.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of the [AuthRepository] interface. Handles communication with the authentication
 * web service and maps DTOs to domain models.
 */
@Singleton
class AuthRepositoryImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val authWebService: AuthWebService,
) : AuthRepository {
    /**
     * Logs in a user with the provided email and password.
     *
     * This function interacts with the authentication web service to attempt a login.
     * It handles the result, converting a successful response UserDto to a domain model
     * and returning it wrapped in a `Result.success`. If the login fails due to any exception,
     * it returns a `Result.failure` containing the exception.
     */
    override suspend fun login(email: String, password: String): Result<User> {
        return withContext(ioDispatcher) {
            authWebService.login(email, password).fold(
                onSuccess = { userDto ->
                    Result.success(userDto.toModel())
                },
                onFailure = { exception -> Result.failure(exception) }
            )
        }
    }

    /**
     * Registers a new user with the provided email and password.
     *
     * This function communicates with the authentication web service to create a new user account.
     * It executes the registration process. On successful registration, it converts the
     * received [UserDto] into a [User] model object and returns it wrapped in a `Result.success`.
     * If an error occurs during registration, it returns a `Result.failure` containing the exception.
     */
    override suspend fun register(email: String, password: String): Result<User> {
        return withContext(ioDispatcher) {
            authWebService.register(email, password).fold(
                onSuccess = { userDto ->
                    Result.success(userDto.toModel())
                },
                onFailure = { exception -> Result.failure(exception) }
            )
        }
    }
}
