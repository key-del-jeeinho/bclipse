package com.bclipse.monolith.application.application.dto

import com.bclipse.monolith.application.application.entity.TossApiVersion

data class AddTossApplicationDto(
    val requesterId: String,
    val applicationId: String,
    val clientKey: String,
    val secretKey: String,
    val version: TossApiVersion,
)
