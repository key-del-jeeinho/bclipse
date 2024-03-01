package com.bclipse.lib.application.dto.request

import com.bclipse.lib.application.dto.command.AddTossApplicationDto
import com.bclipse.lib.application.entity.TossApiVersion

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
