package com.bclipse.application.server.dto

import com.bclipse.application.common.domain.Base64UUID
import com.bclipse.application.server.entity.Server
import java.time.ZonedDateTime

data class ServerDto(
    val serverId: Base64UUID,
    val name: String,
    val description: String,
    val createdAt: ZonedDateTime,
) {
    companion object {
        fun Server.toDto(): ServerDto = ServerDto(
            serverId = serverId,
            name = name,
            description = description,
            createdAt = createdAt
        )
    }
}
