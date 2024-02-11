package com.bclipse.monolith.application.server

import com.bclipse.monolith.common.entity.Base64UUID
import com.bclipse.monolith.infra.web.WebPrecondition.requireRequest
import com.bclipse.monolith.application.server.dto.CreateServerDto
import com.bclipse.monolith.application.server.dto.ServerDto
import com.bclipse.monolith.application.server.dto.ServerDto.Companion.toDto
import com.bclipse.monolith.application.server.entity.Server
import com.bclipse.monolith.application.server.repository.ServerRepository
import com.bclipse.monolith.application.user.UserQueryService
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class ServerService(
    private val serverRepository: ServerRepository,
    private val userQueryService: UserQueryService
) {
    fun create(request: CreateServerDto): ServerDto {
        val isExistingUser = userQueryService.existsById(request.ownerId)
        requireRequest(isExistingUser) { "존재하지 않는 유저입니다! - '${request.ownerId}'" }

        val serverId = Base64UUID.generate()
        val toCreate = Server(
            id = ObjectId(),
            serverId = serverId,
            ownerId = request.ownerId,
            name = request.name,
            description = request.description,
            createdAt = ZonedDateTime.now(),
        )
        val server = serverRepository.save(toCreate)
        return server.toDto()
    }
}
