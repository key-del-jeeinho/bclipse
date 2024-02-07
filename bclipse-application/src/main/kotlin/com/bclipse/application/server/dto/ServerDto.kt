package com.bclipse.application.server.dto

import com.bclipse.application.common.entity.Base64UUID
import com.bclipse.application.server.entity.Server
import java.time.ZonedDateTime

data class ServerDto(
    val serverId: Base64UUID,
    val ownerId: String,
    val name: String,
    val description: String,
    val createdAt: ZonedDateTime,
) {
    companion object {
        fun Server.toDto(): ServerDto = ServerDto(
            serverId = serverId,
            ownerId = ownerId,
            name = name,
            description = description,
            createdAt = createdAt
        )
    }
}
