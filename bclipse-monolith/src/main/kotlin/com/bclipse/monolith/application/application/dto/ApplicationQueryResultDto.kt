package com.bclipse.monolith.application.application.dto

import com.bclipse.monolith.common.entity.Base64UUID

fun interface ApplicationQueryResultDto {
    fun getQueryResultId(): Base64UUID
}