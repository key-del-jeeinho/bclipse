package com.bclipse.lib.application.dto.command

data class AuthApplicationDto(
    val requesterId: String,
    val applicationId: String,
    val timestamp: Long,
    val applicationSecretSign: String,
)
