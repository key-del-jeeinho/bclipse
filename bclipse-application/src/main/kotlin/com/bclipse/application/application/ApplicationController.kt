package com.bclipse.application.application

import com.bclipse.application.application.dto.AuthApplicationDto
import com.bclipse.application.application.dto.CreateApplicationDto
import com.bclipse.application.application.dto.SimpleApplicationAccessTokenDto
import com.bclipse.application.application.dto.UnsecuredApplicationDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "Application API")
@RequestMapping("/api/v1/applications")
class ApplicationController(
    private val applicationService: ApplicationService,
    private val applicationAuthService: ApplicationAuthService,
) {
    @Operation(summary = "어플리케이션 생성")
    @PostMapping
    fun create(
        @RequestBody request: CreateApplicationDto,
    ): ResponseEntity<UnsecuredApplicationDto> {
        val result = applicationService.create(request)
        return ResponseEntity.ok(result)
    }

    //내 어플리케이션 조회
    //어플리케이션 단건 조회
    //어플리케이션 시크릿 변경

    @Operation(summary = "어플리케이션 인증토큰 발급/갱신")
    @PostMapping("/auth/token")
    fun authorize(
        @RequestBody request: AuthApplicationDto,
    ): ResponseEntity<SimpleApplicationAccessTokenDto> {
        val result = applicationAuthService.authorize(request)
        return ResponseEntity.ok(result)
    }
}