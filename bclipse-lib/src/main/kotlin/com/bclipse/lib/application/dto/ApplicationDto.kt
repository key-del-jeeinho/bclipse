package com.bclipse.lib.application.dto

import com.bclipse.lib.application.dto.query.ApplicationQueryResultDto
import com.bclipse.lib.common.entity.BCryptHash
import com.bclipse.lib.common.entity.Base64UUID
import java.time.ZonedDateTime

data class ApplicationDto(
    val serverId: Base64UUID,
    val applicationId: Base64UUID,
    val applicationSecret: BCryptHash,
    val createdAt: ZonedDateTime,
    val secretUpdateAt: ZonedDateTime,
    val secretExpiredAt: ZonedDateTime,
    val setting: ApplicationSettingDto,
): ApplicationQueryResultDto {
    override fun getQueryResultId(): Base64UUID = applicationId
}