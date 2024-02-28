package com.bclipse.monolith.application.application.dto.request

import com.bclipse.monolith.application.application.dto.AddTossApplicationDto
import com.bclipse.monolith.application.application.entity.TossApiVersion

class AddTossApplicationRequest(
    val clientKey: String,
    val secretKey: String,
    val version: TossApiVersion,
) {
    fun toDto(requesterId: String, applicationId: String): AddTossApplicationDto =
        AddTossApplicationDto(
            requesterId = requesterId,
            applicationId = applicationId,
            clientKey = clientKey,
            secretKey = secretKey,
            version = version,
        )
}
