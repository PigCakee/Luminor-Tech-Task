package com.app.demo.storage.shared.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserDbo(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val username: String,
    val email: String,
)
