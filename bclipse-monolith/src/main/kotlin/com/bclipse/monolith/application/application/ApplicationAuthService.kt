package com.bclipse.monolith.application.application

import com.bclipse.monolith.application.application.util.ApplicationAuthUtil.requireRequestSecretSign
import com.bclipse.monolith.application.application.util.ApplicationAuthUtil.requireStateSecretNotExpired
import com.bclipse.monolith.application.application.dto.command.AuthApplicationDto
import com.bclipse.monolith.application.application.dto.SimpleApplicationAccessTokenDto
import com.bclipse.monolith.application.application.entity.Application
import com.bclipse.monolith.application.application.entity.ApplicationAccessToken
import com.bclipse.monolith.application.application.repository.ApplicationAccessTokenRepository
import com.bclipse.monolith.application.application.repository.ApplicationRepository
import com.bclipse.monolith.common.entity.Base64UUID
import com.bclipse.monolith.infra.web.WebPrecondition.checkState
import com.bclipse.monolith.infra.web.WebPrecondition.requirePermission
import com.bclipse.monolith.infra.web.WebPrecondition.requireRequest
import com.bclipse.monolith.application.server.ServerQueryService
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.ZonedDateTime

private val REFRESH_BEFORE = Duration.ofMinutes(30)
private val EXPIRE_IN = Duration.ofHours(4)

@Service
class ApplicationAuthService(
    private val applicationRepository: ApplicationRepository,
    private val applicationAccessTokenRepository: ApplicationAccessTokenRepository,
    private val serverQueryService: ServerQueryService,
) {
    fun authorize(dto: AuthApplicationDto): SimpleApplicationAccessTokenDto {
        val application = applicationRepository.findByApplicationId(dto.applicationId)
        requireRequest(application != null) { "applicationId가 잘못되었습니다." }

        val server = serverQueryService.queryById(application.serverId)
        checkState(server != null) { "serverId가 잘못되었습니다. - '${application.serverId}'" }

        val isOwner = dto.requesterId == server.ownerId
        requirePermission(isOwner) { "작업을 요청할 권한이 없습니다. - '${dto.requesterId}'" }

        requireStateSecretNotExpired(application)
        requireRequestSecretSign(dto, application)

        val token = applicationAccessTokenRepository
            .findByApplicationId(dto.applicationId)
            ?: createAccessToken(application)

        val needRefreshAt = token.expireAt.minus(com.bclipse.monolith.application.application.REFRESH_BEFORE)
        val needRefresh = ZonedDateTime.now().isAfter(needRefreshAt)
        if(needRefresh) refreshAccessToken(token)

        return SimpleApplicationAccessTokenDto(
            accessToken = token.accessToken.value,
            expireAt = token.expireAt,
        )
    }

    private fun createAccessToken(
        dto: Application
    ): ApplicationAccessToken {
        val now = ZonedDateTime.now()
        val newAccessToken = Base64UUID.generate()

        val token = ApplicationAccessToken(
            id = ObjectId(),
            applicationId = dto.applicationId,
            accessToken = newAccessToken,
            expireAt = now.plus(com.bclipse.monolith.application.application.EXPIRE_IN),
            lastRefreshedAt = now
        )

        return applicationAccessTokenRepository.save(token)
    }

    private fun refreshAccessToken(
        token: ApplicationAccessToken,
    ): ApplicationAccessToken {
        val newAccessToken = Base64UUID.generate()

        token.refreshAccessToken(newAccessToken, ZonedDateTime.now(),
            com.bclipse.monolith.application.application.EXPIRE_IN
        )
        return applicationAccessTokenRepository.save(token)
    }
}
