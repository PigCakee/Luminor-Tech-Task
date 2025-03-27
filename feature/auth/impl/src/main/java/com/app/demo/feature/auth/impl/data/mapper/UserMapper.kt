package com.app.demo.feature.auth.impl.data.mapper

import com.app.demo.feature.auth.impl.data.dto.UserDto
import com.app.demo.feature.auth.shared.domain.model.User
import com.app.demo.storage.shared.model.UserDbo

fun UserDto.toModel() = User(
    id = id.toString(),
    email = email,
    username = username
)

fun User.toDbo() = com.app.demo.storage.shared.model.UserDbo(
    id = id,
    email = email,
    username = username
)

fun com.app.demo.storage.shared.model.UserDbo.toModel() = User(
    id = id,
    email = email,
    username = username
)