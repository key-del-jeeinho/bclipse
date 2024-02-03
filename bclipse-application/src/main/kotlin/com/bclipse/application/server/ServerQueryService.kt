package com.bclipse.application.server

import com.bclipse.application.common.domain.Base64UUID
import com.bclipse.application.server.dto.ServerDto
import com.bclipse.application.server.dto.ServerDto.Companion.toDto
import org.springframework.stereotype.Service

@Service
class ServerQueryService(
    private val serverRepository: ServerRepository
) {
    fun queryById(serverId: Base64UUID): ServerDto? =
        serverRepository.findByServerId(serverId.value)?.toDto()
}