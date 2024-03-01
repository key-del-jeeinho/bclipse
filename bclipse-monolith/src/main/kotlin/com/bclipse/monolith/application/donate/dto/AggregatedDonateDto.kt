package com.bclipse.monolith.application.donate.dto

import com.bclipse.lib.application.dto.query.ApplicationQueryResultDto
import com.bclipse.monolith.application.donate.entity.DonateStatus
import com.bclipse.monolith.application.donate.entity.DonateType
import com.bclipse.monolith.application.user.dto.query.UserQueryResultDto
import com.bclipse.lib.common.entity.Base64UUID

data class AggregatedDonateDto(
    val id: Base64UUID,
    val application: ApplicationQueryResultDto,
    val donor: UserQueryResultDto,
    val amount: Int,
    val type: DonateType,
    val status: DonateStatus,
)
