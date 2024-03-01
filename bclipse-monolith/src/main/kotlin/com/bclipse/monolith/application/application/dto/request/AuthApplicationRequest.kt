package com.bclipse.monolith.application.application.dto.request

import com.bclipse.monolith.application.application.dto.command.AuthApplicationDto

data class AuthApplicationRequest(
    val timestamp: Long,
    val applicationSecretSign: String,
) {
    fun toDto(
        requesterId: String,
        applicationId: String
    ): AuthApplicationDto = AuthApplicationDto(
        requesterId = requesterId,
        applicationId = applicationId,
        timestamp = timestamp,
        applicationSecretSign = applicationSecretSign,
    )
}