package com.bclipse.monolith.application.plugin

import com.bclipse.monolith.application.plugin.dto.CreatePluginDto
import com.bclipse.monolith.application.plugin.dto.CreatePluginUrlDto
import com.bclipse.monolith.application.plugin.dto.PluginDto
import com.bclipse.monolith.application.plugin.dto.PluginUrlDto
import com.bclipse.monolith.application.plugin.dto.advance.PluginDetailDto
import com.bclipse.monolith.application.plugin.dto.advance.PluginSummaryDto
import com.bclipse.monolith.infra.security.UserDetailsAdapter
import com.bclipse.monolith.infra.web.ListResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@Tag(name = "Plugin API")
@RequestMapping("/api/v1/plugins")
class PluginController(
    private val pluginService: PluginService,
    private val pluginQueryService: PluginQueryService,
) {
    @Operation(summary = "전체 플러그인 조회")
    @GetMapping()
    fun queryAll(): ResponseEntity<ListResponse<PluginSummaryDto>> {
        val result = pluginQueryService.queryAll()
        return ResponseEntity.ok(
            ListResponse(result)
        )
    }

    @Operation(summary = "플러그인 단건 조회")
    @GetMapping("/{pluginId}")
    fun query(@PathVariable pluginId: String): ResponseEntity<PluginDetailDto> {
        val result = pluginQueryService.queryById(pluginId)
        return ResponseEntity.ok(result)
    }

    @Operation(summary = "신규 플러그인 등록")
    @PostMapping()
    fun create(
        @RequestBody request: CreatePluginDto,
    ): ResponseEntity<PluginDto> {
        val result = pluginService.create(request)
        return ResponseEntity.ok(result)
    }

    @Operation(summary = "플러그인 신규버전 업로드 URL 생성")
    @PostMapping("/{pluginId}/versions/{version}/upload-urls")
    fun createUploadUrl(
        @PathVariable pluginId: String,
        @PathVariable version: String,
        @AuthenticationPrincipal userDetail: UserDetailsAdapter,
    ): ResponseEntity<PluginUrlDto> {
        val dto = CreatePluginUrlDto(
            pluginId = pluginId,
            version = version,
            requesterId = userDetail.userId,
        )
        val result = pluginService.createUploadUrl(dto)
        return ResponseEntity.ok(result)
    }

    @Operation(summary = "플러그인 다운로드 URL 생성")
    @PostMapping("/{pluginId}/versions/{version}/download-urls")
    fun createDownloadUrl(
        @PathVariable pluginId: String,
        @PathVariable version: String,
        @AuthenticationPrincipal userDetail: UserDetailsAdapter,
    ): ResponseEntity<PluginUrlDto> {
        val dto = CreatePluginUrlDto(
            pluginId = pluginId,
            version = version,
            requesterId = userDetail.userId,
        )
        val result = pluginService.createDownloadUrl(dto)
        return ResponseEntity.ok(result)
    }
}