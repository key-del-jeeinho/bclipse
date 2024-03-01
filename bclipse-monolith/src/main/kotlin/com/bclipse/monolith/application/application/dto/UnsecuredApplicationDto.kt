package com.bclipse.monolith.application.application.dto

import com.bclipse.monolith.application.application.dto.query.ApplicationQueryResultDto
import com.bclipse.monolith.application.application.entity.Application
import com.bclipse.monolith.common.entity.BCryptHash
import com.bclipse.monolith.common.entity.Base64UUID
import java.time.ZonedDateTime

data class UnsecuredApplicationDto(
    val serverId: Base64UUID,
    val applicationId: Base64UUID,
    val applicationSecret: BCryptHash,
    val createdAt: ZonedDateTime,
    val secretUpdateAt: ZonedDateTime,
    val secretExpiredAt: ZonedDateTime,
): ApplicationQueryResultDto {
    companion object {
        fun Application.toUnsecuredDto() =
            UnsecuredApplicationDto(
                serverId = serverId,
                applicationId = applicationId,
                applicationSecret = applicationSecret,
                createdAt = createdAt,
                secretUpdateAt = secretUpdatedAt,
                secretExpiredAt = secretExpireAt,
            )
    }

    override fun getQueryResultId(): Base64UUID = applicationId
}