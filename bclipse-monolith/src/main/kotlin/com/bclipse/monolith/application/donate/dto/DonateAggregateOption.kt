package com.bclipse.monolith.application.donate.dto

import com.bclipse.monolith.application.application.dto.query.ApplicationAggregateType
import com.bclipse.monolith.application.user.dto.query.UserAggregateType

data class DonateAggregateOption(
    val application: ApplicationAggregateType,
    val donor: UserAggregateType,
)