package com.app.demo.feature.auth.shared.domain.usecase

import com.app.demo.feature.auth.shared.domain.model.User

/**
 * Use case responsible for saving a logged-in user's information to maintain the logged-in state across app sessions.
 */
interface SaveLoggedInUserUseCase {
    suspend operator fun invoke(user: User)
}