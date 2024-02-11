package com.bclipse.monolith.application.application.dto

import java.time.ZonedDateTime

data class SimpleApplicationAccessTokenDto(
    val accessToken: String,
    val expireAt: ZonedDateTime,
)
