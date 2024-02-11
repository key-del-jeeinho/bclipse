package com.bclipse.monolith.application.application.dto.request

import com.bclipse.monolith.application.application.dto.CreateApplicationDto

data class CreateApplicationRequest(
    val serverId: String,
) {
    fun toDto(requesterId: String): CreateApplicationDto = CreateApplicationDto(
        requesterId = requesterId,
        serverId = serverId,
    )
}