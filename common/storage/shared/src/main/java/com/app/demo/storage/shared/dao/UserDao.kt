package com.app.demo.storage.shared.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.demo.storage.shared.model.UserDbo

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    suspend fun getUsers(): List<UserDbo>

    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun getUserById(id: String): UserDbo?

    @Query("SELECT * FROM user WHERE email = :email")
    suspend fun getUserByEmail(email: String): UserDbo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserDbo)

    @Delete
    suspend fun removeUser(user: UserDbo)
}