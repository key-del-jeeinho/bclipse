package com.bclipse.application.application.request

import com.bclipse.application.application.dto.CreateApplicationDto

data class CreateApplicationRequest(
    val serverId: String,
) {
    fun toDto(requesterId: String): CreateApplicationDto = CreateApplicationDto(
        requesterId = requesterId,
        serverId = serverId,
    )
}