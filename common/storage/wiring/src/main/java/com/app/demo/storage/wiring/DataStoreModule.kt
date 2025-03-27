package com.app.demo.storage.wiring

import com.app.demo.storage.impl.datastore.DataStoreManagerImpl
import com.app.demo.storage.shared.datastore.DataStoreManager
import com.app.demo.storage.shared.datastore.loggedInUser.LoggedInUserManager
import com.app.demo.storage.shared.datastore.loggedInUser.LoggedInUserProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataStoreModule {
    @Binds
    fun bindDataStoreManager(dataStoreManagerImpl: DataStoreManagerImpl): DataStoreManager

    @Binds
    fun bindLoggedInUserProvider(dataStoreManagerImpl: DataStoreManagerImpl): LoggedInUserProvider

    @Binds
    fun bindLoggedInUserManager(dataStoreManagerImpl: DataStoreManagerImpl): LoggedInUserManager
}