package com.bclipse.application.application

import com.bclipse.application.application.ApplicationAuthUtil.validateSecretNotExpired
import com.bclipse.application.common.Base64UUID
import com.bclipse.application.application.ApplicationAuthUtil.validateSecretSign
import com.bclipse.application.application.dto.AuthApplicationDto
import com.bclipse.application.application.dto.SimpleApplicationAccessTokenDto
import com.bclipse.application.application.entity.Application
import com.bclipse.application.application.entity.ApplicationAccessToken
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
) {
    fun authorize(dto: AuthApplicationDto): SimpleApplicationAccessTokenDto {
        val application = applicationRepository.findByApplicationId(dto.applicationId)
            ?: throw RuntimeException("applicationId가 잘못되었습니다.") //TODO

        validateSecretNotExpired(application)
        validateSecretSign(dto, application)

        val token = applicationAccessTokenRepository
            .findByApplicationId(dto.applicationId)
            ?: createAccessToken(application)

        val needRefreshAt = token.expireAt.minus(REFRESH_BEFORE)
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
            expireAt = now.plus(EXPIRE_IN),
            lastRefreshedAt = now
        )

        return applicationAccessTokenRepository.save(token)
    }

    private fun refreshAccessToken(
        token: ApplicationAccessToken,
    ): ApplicationAccessToken {
        val newAccessToken = Base64UUID.generate()

        token.refreshAccessToken(newAccessToken, ZonedDateTime.now(), EXPIRE_IN)
        return applicationAccessTokenRepository.save(token)
    }
}
