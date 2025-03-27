package com.app.demo.feature.auth.impl.domain.usecase

import com.app.demo.feature.auth.shared.domain.model.User
import com.app.demo.feature.auth.shared.domain.repository.UserRepository
import com.app.demo.feature.auth.shared.domain.usecase.SaveLoggedInUserUseCase
import com.app.demo.storage.shared.datastore.loggedInUser.LoggedInUserManager
import javax.inject.Inject

/**
 * This class is responsible for saving a user and marking them as logged in.
 */
class SaveLoggedInUserUseCaseImpl @Inject constructor(
    private val loggedInUserManager: LoggedInUserManager,
    private val userRepository: UserRepository,
) : SaveLoggedInUserUseCase {
    override suspend fun invoke(user: User) {
        userRepository.saveUser(user)
        loggedInUserManager.saveLoggedInUserId(user.id)
    }
}