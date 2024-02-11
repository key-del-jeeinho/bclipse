package com.bclipse.monolith.application.application.dto

import com.bclipse.monolith.common.entity.BCryptHash
import com.bclipse.monolith.common.entity.Base64UUID
import com.bclipse.monolith.application.application.entity.Application
import java.time.ZonedDateTime

data class UnsecuredApplicationDto(
    val serverId: Base64UUID,
    val applicationId: Base64UUID,
    val applicationSecret: BCryptHash,
    val createdAt: ZonedDateTime,
    val secretUpdateAt: ZonedDateTime,
    val secretExpiredAt: ZonedDateTime,
) {
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
}