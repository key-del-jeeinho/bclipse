package com.bclipse.application.server

import com.bclipse.application.common.entity.Base64UUID
import com.bclipse.application.infra.security.UserDetailsAdapter
import com.bclipse.application.server.dto.QueryServerDto
import com.bclipse.application.server.dto.ServerDto
import com.bclipse.application.server.dto.request.CreateServerRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@Tag(name = "Server API")
@RequestMapping("/api/v1/server")
class ServerController(
    private val serverService: ServerService,
    private val serverQueryService: ServerQueryService,
) {
    @Operation(summary = "서버 단건 조회")
    @GetMapping("/{serverId}")
    fun queryById(
        @PathVariable serverId: String,
        @AuthenticationPrincipal userDetail: UserDetailsAdapter,
    ): ResponseEntity<ServerDto> {
        val dto = QueryServerDto(
            serverId = Base64UUID.fromEncodedString(serverId),
            userId = userDetail.userId,
        )
        val result = serverQueryService.queryById(dto)
        return ResponseEntity.ok(result)
    }

    @Operation(summary = "내 서버 목록 조회")
    @GetMapping("/my")
    fun my(
        @AuthenticationPrincipal userDetail: UserDetailsAdapter,
    ): ResponseEntity<List<ServerDto>> {
        val result = serverQueryService.queryByOwnerId(userDetail.userId)
        return ResponseEntity.ok(result)
    }

    @Operation(summary = "서버 생성")
    @PostMapping
    fun create(
        @RequestBody request: CreateServerRequest,
        @AuthenticationPrincipal userDetail: UserDetailsAdapter,
    ): ResponseEntity<ServerDto> {
        val dto = request.toDto(userDetail.userId)

        val result = serverService.create(dto)

        return ResponseEntity.ok(result)
    }
}