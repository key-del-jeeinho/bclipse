package com.bclipse.application.user.util

import com.bclipse.application.user.dto.SecuredUserDto
import com.bclipse.application.user.dto.UserDto
import com.bclipse.application.user.dto.UserProfileDto
import java.time.ZoneId
import java.time.ZonedDateTime

object DefaultUser {
    const val userId = "@default_user"
    const val name = "admin"
    val createdAt = ZonedDateTime.of(
        2024, 1, 1,
        0, 0, 0, 0,
        ZoneId.of("UTC+09")
    )
    const val encodedPassword = "$2a$10$5kaSG4e1ARLD2MKNpqupIe9dvmaMBbdUCXe1eaQANUZCvEDBhljQO"

    fun queryCurrentUserId() = userId

    fun toSecuredDto(): SecuredUserDto = SecuredUserDto(
        id = userId,
        name = name,
        createdAt = createdAt,
    )

    fun toProfileDto(): UserProfileDto = UserProfileDto(
        id = userId,
        name = name,
        createdAt = createdAt,
    )

    fun toDto(): UserDto = UserDto(
        id = userId,
        name = name,
        encodedPassword = encodedPassword,
        createdAt = createdAt,
    )
}