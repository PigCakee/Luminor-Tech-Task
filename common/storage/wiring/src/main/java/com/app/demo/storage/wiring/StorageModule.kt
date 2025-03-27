package com.app.demo.storage.wiring

import android.content.Context
import androidx.room.Room
import com.app.demo.storage.impl.db.DemoDatabase
import com.app.demo.storage.shared.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): DemoDatabase =
        Room.databaseBuilder(
            context = context,
            klass = DemoDatabase::class.java,
            name = "demo-db",
        ).build()

    @Provides
    fun provideUserDao(database: DemoDatabase): UserDao = database.userDao()
}
