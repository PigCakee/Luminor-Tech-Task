package com.app.demo.storage.shared.datastore.loggedInUser

/**
 * Provides access to the currently logged-in user's ID.
 */
interface LoggedInUserProvider {
    suspend fun getLoggedInUserId(): String?
}