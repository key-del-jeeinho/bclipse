package com.bclipse.application.application

import com.bclipse.application.application.dto.SimpleApplicationAccessTokenDto
import com.bclipse.application.application.dto.UnsecuredApplicationDto
import com.bclipse.application.application.dto.request.AddPluginRequest
import com.bclipse.application.application.dto.request.AuthApplicationRequest
import com.bclipse.application.application.dto.request.CreateApplicationRequest
import com.bclipse.application.user.util.DefaultUser.queryCurrentUserId
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@Tag(name = "Application API")
@RequestMapping("/api/v1/applications")
class ApplicationController(
    private val applicationService: ApplicationService,
    private val applicationAuthService: ApplicationAuthService,
    private val applicationSettingService: ApplicationSettingService,
) {
    @Operation(summary = "어플리케이션 생성")
    @PostMapping
    fun create(
        @RequestBody request: CreateApplicationRequest,
    ): ResponseEntity<UnsecuredApplicationDto> {
        val requesterId = queryCurrentUserId()
        val dto = request.toDto(requesterId)

        val result = applicationService.create(dto)

        return ResponseEntity.ok(result)
    }

    //내 어플리케이션 조회
    //어플리케이션 단건 조회
    //어플리케이션 시크릿 변경

    @Operation(summary = "어플리케이션 인증토큰 발급/갱신")
    @PostMapping("/{applicationId}/auth-token")
    fun authorize(
        @RequestBody request: AuthApplicationRequest,
        @PathVariable("applicationId") applicationId: String
    ): ResponseEntity<SimpleApplicationAccessTokenDto> {
        val requesterId = queryCurrentUserId()
        val dto = request.toDto(
            requesterId = requesterId,
            applicationId = applicationId
        )

        val result = applicationAuthService.authorize(dto)

        return ResponseEntity.ok(result)
    }

    @Operation(summary = "플러그인 추가")
    @PostMapping("/{applicationId}/setting/version")
    fun addPlugin(
        @RequestBody request: AddPluginRequest,
        @PathVariable("applicationId") applicationId: String,
    ): ResponseEntity<UnsecuredApplicationDto> {
        val requesterId = queryCurrentUserId()
        val dto = request.toDto(
            requesterId = requesterId,
            applicationId = applicationId,
        )

        val result = applicationSettingService.addPlugin(dto)

        return ResponseEntity.ok(result)
    }
}