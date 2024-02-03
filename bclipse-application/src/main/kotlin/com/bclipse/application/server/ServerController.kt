package com.bclipse.application.server

import com.bclipse.application.server.dto.CreateServerDto
import com.bclipse.application.server.dto.ServerDto
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
        @RequestBody request: CreateServerDto,
    ): ResponseEntity<ServerDto> {
        val result = serverService.create(request)
        return ResponseEntity.ok(result)
    }
}