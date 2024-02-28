package com.bclipse.monolith.application.donate

import com.bclipse.monolith.application.donate.dto.request.ManualAccountTransferDonateRequest
import com.bclipse.monolith.infra.security.UserDetailsAdapter
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "Donate API")
@RequestMapping("/api/v1/donates")
class DonateController(
    private val donateService: DonateService,
) {
    //수동후원(계좌이체) 신청
    @Operation(summary = "수동후원(계좌이체) 신청")
    @PostMapping
    fun donateByManualAccountTransfer(
        @RequestBody request: ManualAccountTransferDonateRequest,
        @AuthenticationPrincipal userDetail: UserDetailsAdapter,
    ): ResponseEntity<Unit> {
        val dto = request.toDto(userDetail.userId)

        val result = donateService.donateByManualAccountTransfer(dto)

        return ResponseEntity.ok(result)
    }
    //수동후원 확인
    //자동후원
    //내 후원내역 조회
}