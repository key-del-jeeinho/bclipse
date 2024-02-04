package com.bclipse.application.application.dto

data class AuthApplicationDto(
    val requesterId: String,
    val applicationId: String,
    val timestamp: Long,
    val applicationSecretSign: String,
)
