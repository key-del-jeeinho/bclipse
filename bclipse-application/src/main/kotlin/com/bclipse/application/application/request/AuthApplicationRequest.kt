package com.bclipse.application.application.request

import com.bclipse.application.application.dto.AuthApplicationDto

data class AuthApplicationRequest(
    val applicationId: String,
    val timestamp: Long,
    val applicationSecretSign: String,
) {
    fun toDto(requesterId: String): AuthApplicationDto = AuthApplicationDto(
        requesterId = requesterId,
        applicationId = applicationId,
        timestamp = timestamp,
        applicationSecretSign = applicationSecretSign,
    )
}