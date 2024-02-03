package com.bclipse.application.application

import com.bclipse.application.application.dto.CreateApplicationDto
import com.bclipse.application.application.dto.UnsecuredApplicationDto
import com.bclipse.application.application.dto.UnsecuredApplicationDto.Companion.toUnsecuredDto
import com.bclipse.application.application.entity.Application
import com.bclipse.application.common.domain.BCryptHash
import com.bclipse.application.common.domain.Base64UUID
import com.bclipse.application.server.ServerQueryService
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.ZonedDateTime

private val SECRET_EXPIRE_IN = Duration.ofDays(90)

@Service
class ApplicationService(
    private val applicationRepository: ApplicationRepository,
    private val serverQueryService: ServerQueryService,
) {
    fun create(dto: CreateApplicationDto): UnsecuredApplicationDto {
        val serverId = Base64UUID.fromEncodedString(dto.serverId)
        serverQueryService.queryById(serverId)
            ?: throw RuntimeException("server를 찾을 수 없습니다. - '${dto.serverId}") //TODO

        val applicationId = Base64UUID.generate()
        val applicationSecret = BCryptHash.generate()

        val now = ZonedDateTime.now()
        val secretExpireAt = now.plus(SECRET_EXPIRE_IN)

        val toCreate = Application(
            id = ObjectId(),
            applicationId = applicationId,
            applicationSecret = applicationSecret,
            serverId = serverId,
            createdAt = now,
            secretUpdatedAt = now,
            secretExpireAt = secretExpireAt
        )

        val application = applicationRepository.save(toCreate)
        return application.toUnsecuredDto()
    }
}
