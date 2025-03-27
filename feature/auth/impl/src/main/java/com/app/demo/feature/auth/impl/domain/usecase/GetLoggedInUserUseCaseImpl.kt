package com.app.demo.feature.auth.impl.domain.usecase

import com.app.demo.di.qualifier.IoDispatcher
import com.app.demo.feature.auth.shared.domain.model.User
import com.app.demo.feature.auth.shared.domain.repository.UserRepository
import com.app.demo.feature.auth.shared.domain.usecase.GetLoggedInUserUseCase
import com.app.demo.storage.shared.datastore.loggedInUser.LoggedInUserProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 *  This use case retrieves the currently logged-in user from the data store and user repository.
 */
class GetLoggedInUserUseCaseImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository,
    private val loggedInUserProvider: LoggedInUserProvider,
) : GetLoggedInUserUseCase {
    /**
     * Retrieves the currently logged-in user.
     * This function attempts to retrieve the user's ID from the [loggedInUserProvider]. If an ID is found, it then uses the [userRepository] to fetch the corresponding user details.
     * @return A [Result] object.  If a user is successfully retrieved, it contains the [User] object.  If no user is logged in or retrieval fails, it returns a `Failure` result containing an [Exception].
     */
    override suspend fun invoke(): Result<User> {
        return withContext(ioDispatcher) {
            val user = loggedInUserProvider.getLoggedInUserId()?.let { id ->
                userRepository.getUserById(id)
            }
            if (user != null) {
                Result.success(user)
            } else {
                Result.failure(Exception())
            }
        }
    }
}