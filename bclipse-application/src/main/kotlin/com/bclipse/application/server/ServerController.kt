package com.bclipse.application.server

import com.bclipse.application.server.dto.ServerDto
import com.bclipse.application.server.dto.request.CreateServerRequest
import com.bclipse.application.user.util.DefaultUser.queryCurrentUserId
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "Server API")
@RequestMapping("/api/v1/server")
class ServerController(
    private val serverService: ServerService,
) {
    @Operation(summary = "서버 생성")
    @PostMapping
    fun create(
        @RequestBody request: CreateServerRequest,
    ): ResponseEntity<ServerDto> {
        val currentUserId = queryCurrentUserId()
        val dto = request.toDto(currentUserId)

        val result = serverService.create(dto)

        return ResponseEntity.ok(result)
    }
}