package com.bclipse.monolith.application.application

import com.bclipse.lib.application.dto.ApplicationDto
import com.bclipse.lib.application.dto.SecuredApplicationDto
import com.bclipse.lib.application.dto.SimpleApplicationAccessTokenDto
import com.bclipse.lib.application.dto.query.QueryApplicationDto
import com.bclipse.lib.application.dto.request.AddPluginRequest
import com.bclipse.lib.application.dto.request.AddTossApplicationRequest
import com.bclipse.lib.application.dto.request.AuthApplicationRequest
import com.bclipse.lib.application.dto.request.CreateApplicationRequest
import com.bclipse.monolith.infra.security.UserDetailsAdapter
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@Tag(name = "Application API")
@RequestMapping("/api/v1/applications")
class ApplicationController(
    private val applicationService: ApplicationService,
    private val applicationQueryService: ApplicationQueryService,
    private val applicationAuthService: ApplicationAuthService,
    private val applicationSettingService: ApplicationSettingService,
) {
    @Operation(summary = "어플리케이션 단건 조회")
    @GetMapping("/{applicationId}")
    fun queryById(
        @PathVariable applicationId: String,
        @AuthenticationPrincipal userDetail: UserDetailsAdapter,
    ): ResponseEntity<SecuredApplicationDto> {
        val dto = QueryApplicationDto(
            applicationId = applicationId,
            userId = userDetail.userId,
        )

        val result = applicationQueryService.accessById(dto)

        return ResponseEntity.ok(result)
    }

    @Operation(summary = "어플리케이션 생성")
    @PostMapping
    fun create(
        @RequestBody request: CreateApplicationRequest,
        @AuthenticationPrincipal userDetail: UserDetailsAdapter,
    ): ResponseEntity<ApplicationDto> {
        val dto = request.toDto(userDetail.userId)

        val result = applicationService.create(dto)

        return ResponseEntity.ok(result)
    }

    //어플리케이션 시크릿 변경

    @Operation(summary = "어플리케이션 인증토큰 발급/갱신")
    @PostMapping("/{applicationId}/auth-token")
    @PreAuthorize("permitAll()")
    fun authorize(
        @RequestBody request: AuthApplicationRequest,
        @PathVariable("applicationId") applicationId: String,
    ): ResponseEntity<SimpleApplicationAccessTokenDto> {
        val dto = request.toDto(applicationId)

        val result = applicationAuthService.authorize(dto)

        return ResponseEntity.ok(result)
    }

    @Operation(summary = "플러그인 추가")
    @PostMapping("/{applicationId}/setting/plugins")
    fun addPlugin(
        @RequestBody request: AddPluginRequest,
        @PathVariable("applicationId") applicationId: String,
        @AuthenticationPrincipal userDetail: UserDetailsAdapter,
    ): ResponseEntity<ApplicationDto> {
        val dto = request.toDto(
            requesterId = userDetail.userId,
            applicationId = applicationId,
        )

        val result = applicationSettingService.addPlugin(dto)

        return ResponseEntity.ok(result)
    }

    @Operation(summary = "토스 어플리케이션 추가")
    @PostMapping("/{applicationId}/setting/applications/toss")
    fun addPlugin(
        @RequestBody request: AddTossApplicationRequest,
        @PathVariable("applicationId") applicationId: String,
        @AuthenticationPrincipal userDetail: UserDetailsAdapter,
    ): ResponseEntity<ApplicationDto> {
        val dto = request.toDto(
            requesterId = userDetail.userId,
            applicationId = applicationId,
        )

        val result = applicationSettingService.addTossApplication(dto)

        return ResponseEntity.ok(result)
    }
}