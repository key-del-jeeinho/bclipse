package com.bclipse.application.user.util

import com.bclipse.application.infra.web.WebException
import com.bclipse.application.infra.web.WebPrecondition.preconditionWeb
import com.bclipse.application.user.dto.SecuredUserDto
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.ZonedDateTime
import java.util.*

private const val ACCESS_TOKEN_SUBJECT = "bclipse-access-token"

@Component
class AccessTokenEncoder(
    @Value("\${spring.security.jwt.secret}")
    private val jwtSecret: String,
    @Value("\${spring.security.login.access-token.ttl-second}")
    private val accessTokenTtlSecond: Long,
) {
    fun encode(user: SecuredUserDto): String =
        Jwts.builder()
            .setSubject(ACCESS_TOKEN_SUBJECT)
            .addClaims(mapOf("id" to user.id))
            .setIssuedAt(Date.from(
                ZonedDateTime.now().toInstant()
            )).setExpiration(Date.from(
                ZonedDateTime.now()
                    .plusSeconds(accessTokenTtlSecond)
                    .toInstant()
            ))
            .signWith(
                Keys.hmacShaKeyFor(
                    jwtSecret.toByteArray(StandardCharsets.UTF_8)
                ),
                SignatureAlgorithm.HS256,
            ).compact()
    fun decodeToUserId(accessToken: String): String {
        val lazyMessage = lazy { "잘못된 권한정보입니다." }
        return runCatching {
            Jwts.parserBuilder()
                .setSigningKey(
                    Keys.hmacShaKeyFor(
                        jwtSecret.toByteArray(StandardCharsets.UTF_8)
                    )
                ).build()
                .parseClaimsJws(accessToken)
                .body
                .let { body ->
                    val isRightSubject = body.subject == ACCESS_TOKEN_SUBJECT
                    preconditionWeb(isRightSubject, HttpStatus.UNAUTHORIZED) {
                        IllegalArgumentException(lazyMessage.value)
                    }
                    body["id", String::class.java]
                }
        }.getOrElse { cause ->
            throw WebException(HttpStatus.UNAUTHORIZED, lazyMessage.value, cause)
        }
    }
}
