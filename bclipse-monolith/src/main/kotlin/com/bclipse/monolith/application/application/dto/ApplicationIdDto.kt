package com.bclipse.monolith.application.application.dto

import com.bclipse.monolith.application.application.dto.query.ApplicationQueryResultDto
import com.bclipse.monolith.common.entity.Base64UUID

data class ApplicationIdDto(val id: Base64UUID): ApplicationQueryResultDto {
    companion object {
        fun Base64UUID.toIdDto(): ApplicationIdDto =
            ApplicationIdDto(id = this)
    }

    override fun getQueryResultId(): Base64UUID = id
}