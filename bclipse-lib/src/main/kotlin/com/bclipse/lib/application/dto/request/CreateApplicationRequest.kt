package com.bclipse.lib.application.dto.request

import com.bclipse.lib.application.dto.command.CreateApplicationDto

data class CreateApplicationRequest(
    val serverId: String,
) {
    fun toDto(requesterId: String): CreateApplicationDto = CreateApplicationDto(
        requesterId = requesterId,
        serverId = serverId,
    )
}