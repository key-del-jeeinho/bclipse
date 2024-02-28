package com.bclipse.monolith.application.donate

import com.bclipse.monolith.application.application.ApplicationQueryService
import com.bclipse.monolith.application.application.dto.QueryApplicationDto
import com.bclipse.monolith.application.donate.dto.ConfirmManualDonateDto
import com.bclipse.monolith.application.donate.dto.DonateManualAccountTransferDto
import com.bclipse.monolith.application.donate.entity.DonateStatus
import com.bclipse.monolith.application.donate.entity.DonateType
import com.bclipse.monolith.application.donate.entity.document.DonateDocument
import com.bclipse.monolith.application.donate.entity.document.ManualAccountTransferDonateDocument
import com.bclipse.monolith.application.donate.repository.DonateRepository
import com.bclipse.monolith.application.donate.repository.ManualAccountTransferDonateRepository
import com.bclipse.monolith.common.entity.Base64UUID
import com.bclipse.monolith.infra.web.WebPrecondition
import org.bson.types.ObjectId
import org.springframework.stereotype.Service

@Service
class DonateService(
    private val donateRepository: DonateRepository,
    private val manualAccountTransferDonateRepository: ManualAccountTransferDonateRepository,
    private val applicationQueryService: ApplicationQueryService,
) {
    fun donateByManualAccountTransfer(
        dto: DonateManualAccountTransferDto
    ) {
        WebPrecondition.requireRequest(dto.amount > 0) { "후원금액은 1원 이상이어야합니다." }

        val isExists = applicationQueryService.existsById(dto.applicationId)
        WebPrecondition.requireRequest(isExists) { "applicationId가 잘못되었습니다." }

        val donateId = Base64UUID.generate()

        val donate = DonateDocument(
            id = ObjectId(),
            donateId = donateId,
            applicationId = dto.applicationId,
            donorId = dto.donorId,
            amount = dto.amount,
            type = DonateType.MANUAL_ACCOUNT_TRANSFER,
            status = DonateStatus.REQUESTED,
        )
        val manualAccountTransfer = ManualAccountTransferDonateDocument(
            id = ObjectId(),
            donateId = donateId,
            depositorName = dto.depositorName,
        )

        donateRepository.save(donate)
        manualAccountTransferDonateRepository.save(manualAccountTransfer)
    }

    fun confirmManualDonate(dto: ConfirmManualDonateDto) {
        val donate = donateRepository.findByDonateId(dto.donateId)
        WebPrecondition.requireRequest(donate != null) { "donateId가 잘못되었습니다." }

        applicationQueryService.accessById(QueryApplicationDto(
            applicationId = donate.applicationId,
            userId = dto.requesterId,
        ))

        donate.confirm()

        donateRepository.save(donate)
    }
}
