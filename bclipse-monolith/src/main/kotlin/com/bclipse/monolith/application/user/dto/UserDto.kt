package com.bclipse.monolith.application.user.dto

import com.bclipse.monolith.application.user.entity.User
import java.time.ZonedDateTime

data class UserDto(
    val id: String,
    val name: String,
    val encodedPassword: String,
    val createdAt: ZonedDateTime,
) {
    companion object {
        fun User.toDto(): UserDto =
            UserDto(
                id = userId,
                name = name,
                encodedPassword = encodedPassword,
                createdAt = createdAt
            )
    }
}
