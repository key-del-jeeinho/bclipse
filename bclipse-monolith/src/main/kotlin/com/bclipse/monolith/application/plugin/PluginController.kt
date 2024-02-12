package com.bclipse.monolith.application.plugin

import com.bclipse.monolith.application.plugin.dto.CreatePluginDto
import com.bclipse.monolith.application.plugin.dto.CreatePluginUrlDto
import com.bclipse.monolith.application.plugin.dto.PluginDto
import com.bclipse.monolith.application.plugin.dto.PluginUrlDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@Tag(name = "Plugin API")
@RequestMapping("/api/v1/plugins")
class PluginController(
    private val pluginService: PluginService,
) {
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
    ): ResponseEntity<PluginUrlDto> {
        val dto = CreatePluginUrlDto(pluginId, version)
        val result = pluginService.createUploadUrl(dto)
        return ResponseEntity.ok(result)
    }

    @Operation(summary = "플러그인 다운로드 URL 생성")
    @PostMapping("/{pluginId}/versions/{version}/download-urls")
    fun createDownloadUrl(
        @PathVariable pluginId: String,
        @PathVariable version: String
    ): ResponseEntity<PluginUrlDto> {
        val dto = CreatePluginUrlDto(pluginId, version)
        val result = pluginService.createDownloadUrl(dto)
        return ResponseEntity.ok(result)
    }
}