package com.app.demo.storage.impl.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.demo.storage.shared.dao.UserDao
import com.app.demo.storage.shared.model.UserDbo
import javax.inject.Singleton

@Database(
    entities = [
        UserDbo::class,
    ],
    version = 1,
    autoMigrations = [],
    exportSchema = true
)
@Singleton
abstract class DemoDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}
