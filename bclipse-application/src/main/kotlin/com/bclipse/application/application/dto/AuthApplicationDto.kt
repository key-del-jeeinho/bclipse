package com.bclipse.application.application.dto

data class AuthApplicationDto(
    val applicationId: String,
    val timestamp: Long,
    val applicationSecretSign: String,
)
