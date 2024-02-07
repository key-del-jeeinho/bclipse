package com.bclipse.application.user.dto

import com.bclipse.application.common.entity.Base64UUID

data class LoginTokenDto(
    val accessToken: String,
    val refreshToken: RefreshTokenDto,
)

data class RefreshTokenDto(
    val token: Base64UUID,
    val expireInSecond: Long,
) {
    companion object {
        const val COOKIE_NAME = "refreshToken"
    }
}
