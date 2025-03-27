package com.app.demo

import androidx.multidex.MultiDexApplication
import com.app.demo.di.qualifier.DefaultDispatcher
import com.app.demo.navigation.shared.AuthListener
import com.app.demo.navigation.shared.navigation.NavigationManager
import com.app.demo.navigation.shared.navigation.navigateToSignInUp
import com.app.demo.storage.shared.datastore.DataStoreManager
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltAndroidApp
class App : MultiDexApplication(), AuthListener {

    @Inject
    lateinit var navigationManager: NavigationManager

    @Inject
    lateinit var dataStoreManager: DataStoreManager

    @Inject
    @DefaultDispatcher
    lateinit var defaultDispatcher: CoroutineDispatcher

    override suspend fun signOut() {
        withContext(defaultDispatcher) {
            dataStoreManager.clear()
            navigationManager.navigateToSignInUp()
        }
    }
}
