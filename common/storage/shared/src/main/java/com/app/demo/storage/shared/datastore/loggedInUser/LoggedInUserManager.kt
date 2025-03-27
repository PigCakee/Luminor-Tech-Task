package com.app.demo.storage.shared.datastore.loggedInUser

/**
 *  Manages the currently logged-in user's ID, providing functionality to both access and persist it.
 */
interface LoggedInUserManager: LoggedInUserProvider {
    suspend fun saveLoggedInUserId(userId: String)
}