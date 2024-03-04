package com.bclipse.lib.application.dto.request

import com.bclipse.lib.application.dto.command.AuthApplicationDto

data class AuthApplicationRequest(
    val timestamp: Long,
    val applicationSecretSign: String,
) {
    fun toDto(
        applicationId: String
    ): AuthApplicationDto = AuthApplicationDto(
        applicationId = applicationId,
        timestamp = timestamp,
        applicationSecretSign = applicationSecretSign,
    )
}