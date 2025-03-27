package com.app.demo.feature.auth.shared.domain.usecase

import com.app.demo.feature.auth.shared.domain.model.User

/**
 * Use case responsible for retrieving the currently logged-in user.
 */
interface GetLoggedInUserUseCase {
    suspend operator fun invoke(): Result<User>
}