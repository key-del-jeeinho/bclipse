package com.bclipse.monolith.application.donate

import com.bclipse.monolith.application.donate.dto.DonateDto
import com.bclipse.monolith.application.donate.entity.document.DonateDocument
import com.bclipse.monolith.application.donate.repository.DonateRepository
import org.springframework.stereotype.Service

@Service
class DonateQueryService(
    private val donateRepository: DonateRepository,
) {
    fun queryMyDonates(userId: String): List<DonateDto> {
        val documents = donateRepository.findAllByDonorId(userId)
        return documents.map(DonateDocument::toDto)
    }
}
