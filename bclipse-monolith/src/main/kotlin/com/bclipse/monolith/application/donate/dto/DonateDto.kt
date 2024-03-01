package com.bclipse.monolith.application.donate.dto

import com.bclipse.monolith.application.donate.entity.DonateStatus
import com.bclipse.monolith.application.donate.entity.DonateType
import com.bclipse.monolith.common.entity.Base64UUID

data class DonateDto(
    val id: Base64UUID,
    val applicationId: Base64UUID,
    val donorId: String,
    val amount: Int,
    val type: DonateType,
    val status: DonateStatus,
)
