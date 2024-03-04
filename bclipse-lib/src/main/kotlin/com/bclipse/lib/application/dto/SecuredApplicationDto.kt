package com.bclipse.lib.application.dto

import com.bclipse.lib.application.dto.query.ApplicationQueryResultDto
import com.bclipse.lib.common.entity.Base64UUID
import java.time.ZonedDateTime

data class SecuredApplicationDto(
    val id: Base64UUID,
    val serverId: Base64UUID,
    val createdAt: ZonedDateTime,
    val secretUpdatedAt: ZonedDateTime,
    val secretExpireAt: ZonedDateTime,
    val setting: ApplicationSettingDto,
): ApplicationQueryResultDto {
    override fun getQueryResultId(): Base64UUID = id
}
