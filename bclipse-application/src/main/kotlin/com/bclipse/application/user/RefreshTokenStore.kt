package com.bclipse.application.user

import com.bclipse.application.common.domain.Base64UUID
import com.bclipse.application.user.dto.RefreshTokenDto
import com.bclipse.application.user.entity.RefreshToken
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.ZonedDateTime

@Component
class RefreshTokenStore(
    private val refreshTokenRepository: RefreshTokenRepository,
    @Value("\${spring.security.login.refresh-token.ttl-second}")
    private val refreshTokenTtlSecond: Long,
) {
    fun generate(userId: String): RefreshTokenDto {
        val generated = Base64UUID.generate()
        val expireAt = ZonedDateTime.now()
            .plusSeconds(refreshTokenTtlSecond)

        val toCreate = RefreshToken(
            id = ObjectId(),
            refreshToken = generated.value,
            userId = userId,
            expireAt = expireAt,
        )
        refreshTokenRepository.save(toCreate)

        return RefreshTokenDto(
            refreshToken = generated,
            expireInSecond = refreshTokenTtlSecond
        )
    }
}