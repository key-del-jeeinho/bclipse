package com.bclipse.application.server

import com.bclipse.application.common.domain.Base64UUID
import com.bclipse.application.server.dto.ServerDto.Companion.toDto
import com.bclipse.application.server.dto.CreateServerDto
import com.bclipse.application.server.dto.ServerDto
import com.bclipse.application.server.entity.Server
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class ServerService(
    private val serverRepository: ServerRepository,
) {
    fun create(request: CreateServerDto): ServerDto {
        val serverId = Base64UUID.generate()
        val toCreate = Server(
            id = ObjectId(),
            serverId = serverId,
            name = request.name,
            description = request.description,
            createdAt = ZonedDateTime.now(),
        )
        val server = serverRepository.save(toCreate)
        return server.toDto()
    }
}
