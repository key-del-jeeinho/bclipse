package com.bclipse.monolith.application.application.dto.command

data class AuthApplicationDto(
    val requesterId: String,
    val applicationId: String,
    val timestamp: Long,
    val applicationSecretSign: String,
)
