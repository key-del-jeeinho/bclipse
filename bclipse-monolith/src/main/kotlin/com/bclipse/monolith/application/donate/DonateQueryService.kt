package com.bclipse.monolith.application.donate

import com.bclipse.monolith.application.application.ApplicationQueryService
import com.bclipse.monolith.application.donate.dto.AggregatedDonateDto
import com.bclipse.monolith.application.donate.dto.DonateAggregateOption
import com.bclipse.monolith.application.donate.entity.document.DonateDocument
import com.bclipse.monolith.application.donate.repository.DonateRepository
import com.bclipse.monolith.application.user.UserQueryService
import org.springframework.stereotype.Service

@Service
class DonateQueryService(
    private val donateRepository: DonateRepository,
    private val userQueryService: UserQueryService,
    private val applicationQueryService: ApplicationQueryService,
) {
    fun queryMyDonates(
        userId: String,
        aggregateOption: DonateAggregateOption
    ): List<AggregatedDonateDto> {
        val documents = donateRepository.findAllByDonorId(userId)

        val donorIds = documents.map(DonateDocument::donorId)
        val aggregatedDonors = userQueryService.queryAllByIds(
            donorIds,
            aggregateOption.donor
        )

        val applicationIds = documents.map(DonateDocument::applicationId)
        val aggregatedApplications = applicationQueryService.queryAllByIds(
            applicationIds,
            aggregateOption.application,
        )

        return documents.map { donate ->
            val donor = aggregatedDonors.first { donate.donorId == it.getQueryResultId() }
            val application = aggregatedApplications.first {
                donate.applicationId == it.getQueryResultId()
            }

            AggregatedDonateDto(
                id = donate.donateId,
                application = application,
                donor = donor,
                amount = donate.amount,
                type = donate.type,
                status = donate.status,
            )
        }
    }
}
