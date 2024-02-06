package com.bclipse.application.application.request

import com.bclipse.application.application.dto.AuthApplicationDto

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