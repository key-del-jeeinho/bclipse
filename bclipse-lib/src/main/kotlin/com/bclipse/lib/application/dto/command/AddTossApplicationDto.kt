package com.bclipse.lib.application.dto.command

import com.bclipse.lib.application.entity.TossApiVersion

data class AddTossApplicationDto(
    val requesterId: String,
    val applicationId: String,
    val clientKey: String,
    val secretKey: String,
    val version: TossApiVersion,
)
