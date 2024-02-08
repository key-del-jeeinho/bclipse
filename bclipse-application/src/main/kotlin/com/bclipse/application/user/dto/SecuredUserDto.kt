package com.bclipse.application.user.dto

import com.bclipse.application.user.entity.User
import java.time.ZonedDateTime

data class SecuredUserDto(
    val id: String,
    val name : String,
    val createdAt: ZonedDateTime,
) {
    companion object {
        fun User.toSecuredDto(): SecuredUserDto = SecuredUserDto(
            id = userId,
            name = name,
            createdAt = createdAt
        )

        fun User.toProfileDto(): UserProfileDto =UserProfileDto(
            id = userId,
            name = name,
            createdAt = createdAt
        )
    }
}
