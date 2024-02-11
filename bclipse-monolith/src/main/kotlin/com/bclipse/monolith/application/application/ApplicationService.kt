package com.bclipse.monolith.application.application

import com.bclipse.monolith.application.application.dto.CreateApplicationDto
import com.bclipse.monolith.application.application.dto.UnsecuredApplicationDto
import com.bclipse.monolith.application.application.dto.UnsecuredApplicationDto.Companion.toUnsecuredDto
import com.bclipse.monolith.application.application.entity.Application
import com.bclipse.monolith.application.application.entity.ApplicationSetting
import com.bclipse.monolith.application.application.repository.ApplicationRepository
import com.bclipse.monolith.application.server.ServerQueryService
import com.bclipse.monolith.common.entity.BCryptHash
import com.bclipse.monolith.common.entity.Base64UUID
import com.bclipse.monolith.infra.web.WebException
import com.bclipse.monolith.infra.web.WebPrecondition.requirePermission
import com.bclipse.monolith.infra.web.WebPrecondition.requireRequest
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
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
        val serverId = runCatching { Base64UUID.fromEncodedString(dto.serverId) }
            .getOrElse { throwable -> throw WebException(HttpStatus.BAD_REQUEST, throwable) }

        val server = serverQueryService.queryById(serverId)
        requireRequest(server != null) { "server를 찾을 수 없습니다. - '${dto.serverId}" }

        val isOwner = dto.requesterId == server.ownerId
        requirePermission(isOwner) { "작업을 요청할 권한이 없습니다. - '${dto.requesterId}'" }

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
            secretExpireAt = secretExpireAt,
            setting = ApplicationSetting.DEFAULT,
        )

        val application = applicationRepository.save(toCreate)
        return application.toUnsecuredDto()
    }
}