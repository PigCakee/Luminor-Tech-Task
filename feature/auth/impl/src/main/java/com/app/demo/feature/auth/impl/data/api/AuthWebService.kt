package com.app.demo.feature.auth.impl.data.api

import com.app.demo.feature.auth.impl.data.dto.UserDto

interface AuthWebService {
    // Imagine an API request here (POST)
    suspend fun login(email: String, password: String): Result<UserDto>
    suspend fun register(email: String, password: String): Result<UserDto>
}