package com.bclipse.application.application

import com.bclipse.application.application.dto.CreateApplicationDto
import com.bclipse.application.application.dto.UnsecuredApplicationDto
import com.bclipse.application.application.dto.UnsecuredApplicationDto.Companion.toUnsecuredDto
import com.bclipse.application.application.entity.Application
import com.bclipse.application.common.BCryptHash
import com.bclipse.application.common.Base64UUID
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.ZonedDateTime

private val SECRET_EXPIRE_IN = Duration.ofDays(90)

@Service
class ApplicationService(
    private val applicationRepository: ApplicationRepository,
) {
    fun create(dto: CreateApplicationDto): UnsecuredApplicationDto {
        val applicationId = Base64UUID.generate()
        val applicationSecret = BCryptHash.generate()

        val now = ZonedDateTime.now()
        val secretExpireAt = now.plus(SECRET_EXPIRE_IN)

        val toCreate = Application(
            id = ObjectId(),
            applicationId = applicationId,
            applicationSecret = applicationSecret,
            serverId = Base64UUID.fromEncodedString(dto.serverId),
            createdAt = now,
            secretUpdatedAt = now,
            secretExpireAt = secretExpireAt
        )

        val application = applicationRepository.save(toCreate)
        return application.toUnsecuredDto()
    }
}
