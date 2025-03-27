package com.app.demo.storage.impl.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.app.demo.storage.shared.datastore.DataStoreManager
import com.app.demo.storage.shared.datastore.loggedInUser.LoggedInUserManager
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreManagerImpl @Inject constructor(
    private val context: Context,
): DataStoreManager, LoggedInUserManager {
    private object PreferencesKeys {
        val LOGGED_IN_USER_KEY = stringPreferencesKey("logged_in_user")
    }

    override suspend fun saveLoggedInUserId(userId: String) {
        setValueByKey(PreferencesKeys.LOGGED_IN_USER_KEY, userId)
    }

    override suspend fun getLoggedInUserId(): String? {
        return getValueByKey(PreferencesKeys.LOGGED_IN_USER_KEY)
    }

    override suspend fun clear() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }

    private suspend fun <T> getValueByKey(
        key: Preferences.Key<T>
    ): T? {
        return context.dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[key]
        }.firstOrNull()
    }

    private suspend fun <T> setValueByKey(
        key: Preferences.Key<T>,
        value: T
    ) {
        context.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")