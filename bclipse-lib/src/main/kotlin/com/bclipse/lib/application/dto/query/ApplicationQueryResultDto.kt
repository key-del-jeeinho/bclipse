package com.bclipse.lib.application.dto.query

import com.bclipse.lib.common.entity.Base64UUID

fun interface ApplicationQueryResultDto {
    fun getQueryResultId(): Base64UUID
}