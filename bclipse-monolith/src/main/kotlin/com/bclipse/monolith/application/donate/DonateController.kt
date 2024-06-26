package com.bclipse.monolith.application.donate

import com.bclipse.lib.application.dto.query.ApplicationAggregateType
import com.bclipse.monolith.application.donate.dto.AggregatedDonateDto
import com.bclipse.monolith.application.donate.dto.ConfirmManualDonateDto
import com.bclipse.monolith.application.donate.dto.DonateAggregateOption
import com.bclipse.monolith.application.donate.dto.request.ManualAccountTransferDonateRequest
import com.bclipse.monolith.application.user.dto.query.UserAggregateType
import com.bclipse.monolith.infra.security.UserDetailsAdapter
import com.bclipse.monolith.infra.web.ListResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@Tag(name = "Donate API")
@RequestMapping("/api/v1/donates")
class DonateController(
    private val donateService: DonateService,
    private val donateQueryService: DonateQueryService,
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
    @Operation(summary = "수동후원 확인(후원 성공)")
    @PostMapping("/{donateId}")
    fun confirmManualDonate(
        @PathVariable donateId: String,
        @AuthenticationPrincipal userDetail: UserDetailsAdapter,
    ): ResponseEntity<Unit> {
        val dto = ConfirmManualDonateDto(
            donateId = donateId,
            requesterId = userDetail.userId,
        )
        val result = donateService.confirmManualDonate(dto)

        return ResponseEntity.ok(result)
    }
    //자동후원
    //내 후원내역 조회
    @Operation(summary = "내 후원내역 조회")
    @GetMapping("/my")
    fun queryMyDonates(
        @AuthenticationPrincipal userDetail: UserDetailsAdapter,
        @RequestParam("aggregate.application") application: ApplicationAggregateType,
        @RequestParam("aggregate.donor") donor: UserAggregateType,
    ): ResponseEntity<ListResponse<AggregatedDonateDto>> {
        val result = donateQueryService.queryMyDonates(
            userDetail.userId,
            DonateAggregateOption(application, donor),
        )
        return ResponseEntity.ok(
            ListResponse(result)
        )
    }
}