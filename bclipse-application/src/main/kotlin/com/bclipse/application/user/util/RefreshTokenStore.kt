package com.bclipse.application.user.util

import com.bclipse.application.common.entity.Base64UUID
import com.bclipse.application.user.dto.RefreshTokenDto
import com.bclipse.application.user.entity.RefreshToken
import com.bclipse.application.user.repository.RefreshTokenRepository
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
            token = generated.value,
            userId = userId,
            expireAt = expireAt,
        )
        refreshTokenRepository.save(toCreate)

        return RefreshTokenDto(
            token = generated,
            expireInSecond = refreshTokenTtlSecond
        )
    }

    fun findByTokenAndDelete(refreshTokenString: String): RefreshToken? {
        val refreshToken = refreshTokenRepository.findByToken(refreshTokenString) ?: return null
        refreshTokenRepository.deleteById(refreshToken.id)
        return refreshToken
    }
}