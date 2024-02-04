package com.bclipse.application.user.dto

import com.bclipse.application.common.domain.Base64UUID

data class LoginTokenDto(
    val accessToken: String,
    val refreshToken: RefreshTokenDto,
)

data class RefreshTokenDto(
    val refreshToken: Base64UUID,
    val expireInSecond: Long,
)
