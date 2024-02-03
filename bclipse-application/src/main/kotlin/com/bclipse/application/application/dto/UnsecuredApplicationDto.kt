package com.bclipse.application.application.dto

import com.bclipse.application.common.domain.BCryptHash
import com.bclipse.application.common.domain.Base64UUID
import com.bclipse.application.application.entity.Application
import java.time.ZonedDateTime

data class UnsecuredApplicationDto(
    val serverId: Base64UUID,
    val applicationId: Base64UUID,
    val applicationSecret: BCryptHash,
    val createAt: ZonedDateTime,
    val secretUpdateAt: ZonedDateTime,
    val secretExpiredAt: ZonedDateTime,
) {
    companion object {
        fun Application.toUnsecuredDto() =
            UnsecuredApplicationDto(
                serverId = serverId,
                applicationId = applicationId,
                applicationSecret = applicationSecret,
                createAt = createdAt,
                secretUpdateAt = secretUpdatedAt,
                secretExpiredAt = secretExpireAt,
            )
    }
}