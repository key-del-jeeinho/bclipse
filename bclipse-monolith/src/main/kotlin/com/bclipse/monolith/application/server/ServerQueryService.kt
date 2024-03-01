package com.bclipse.monolith.application.server

import com.bclipse.lib.common.entity.Base64UUID
import com.bclipse.monolith.infra.web.WebException
import com.bclipse.monolith.application.server.dto.QueryServerDto
import com.bclipse.monolith.application.server.dto.ServerDto
import com.bclipse.monolith.application.server.dto.ServerDto.Companion.toDto
import com.bclipse.monolith.application.server.repository.ServerRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class ServerQueryService(
    private val serverRepository: ServerRepository
) {
    fun queryById(serverId: Base64UUID): ServerDto? =
        serverRepository.findByServerId(serverId.value)?.toDto()

    fun queryById(dto: QueryServerDto): ServerDto {
        val exception = lazy {
            WebException(
                httpStatus = HttpStatus.NOT_FOUND,
                message = "서버를 찾을 수 없습니다. - '${dto.serverId}'",
            )
        }

        val server = serverRepository.findByServerId(dto.serverId.value)
            ?: throw exception.value

        val hasPermission = server.ownerId == dto.userId
        if(!hasPermission) throw exception.value

        return server.toDto()
    }

    fun queryByOwnerId(ownerId: String): List<ServerDto> {
        val servers = serverRepository.findAllByOwnerId(ownerId)
        return servers.map { it.toDto() }
    }
}