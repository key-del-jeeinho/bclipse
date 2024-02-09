package com.bclipse.application.application.dto

import com.bclipse.application.application.dto.ApplicationSettingDto.Companion.toDto
import com.bclipse.application.application.entity.Application
import com.bclipse.application.common.entity.Base64UUID
import java.time.ZonedDateTime

data class ApplicationDetailDto(
    val id: Base64UUID,
    val serverId: Base64UUID,
    val createdAt: ZonedDateTime,
    val secretUpdatedAt: ZonedDateTime,
    val secretExpireAt: ZonedDateTime,
    val setting: ApplicationSettingDto,
) {
    companion object {
        fun Application.toDetailDto(): ApplicationDetailDto =
            ApplicationDetailDto(
                id = applicationId,
                serverId = serverId,
                createdAt =  createdAt,
                secretUpdatedAt = secretUpdatedAt,
                secretExpireAt = secretExpireAt,
                setting = setting.toDto(),
            )
    }
}
