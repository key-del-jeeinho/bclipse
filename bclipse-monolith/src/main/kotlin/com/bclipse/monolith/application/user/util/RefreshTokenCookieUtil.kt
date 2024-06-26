package com.bclipse.monolith.application.user.util

import com.bclipse.monolith.application.user.dto.RefreshTokenDto
import org.springframework.http.ResponseCookie

object RefreshTokenCookieUtil {
    fun RefreshTokenDto.toHttpOnlySecuredCookie(
        domain: String,
        path: String,
    ): ResponseCookie =
        ResponseCookie.from(RefreshTokenDto.COOKIE_NAME)
            .value(token.value)
            .maxAge(expireInSecond)
            .domain(domain)
            .path(path)
            .httpOnly(true)
            .secure(true)
            .build()
}