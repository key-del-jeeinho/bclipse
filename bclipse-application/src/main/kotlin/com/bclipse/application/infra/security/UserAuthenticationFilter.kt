package com.bclipse.application.infra.security

import com.bclipse.application.user.util.AccessTokenEncoder
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.filter.OncePerRequestFilter

// Bearer token is need case=insensitive in RFC 6749
//https://datatracker.ietf.org/doc/html/rfc6749#section-5.1
val BEARER_TOKEN_REGEX = Regex("^Bearer (.*)", RegexOption.IGNORE_CASE)

class UserAuthenticationFilter(
    private val accessTokenEncoder: AccessTokenEncoder,
    private val userDetailsService: UserDetailsService,
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = request.getHeader(HttpHeaders.AUTHORIZATION)

        if(
            token.isNullOrEmpty() || !token.matches(BEARER_TOKEN_REGEX)
        ) return filterChain.doFilter(request, response)

        val authentication = BEARER_TOKEN_REGEX.find(token)?.groupValues?.get(1)
            .let {
                if(it.isNullOrBlank()) {
                    logger.warn("BEARER_TOKEN_REGEX is matched, but token is malformed. please check 'BEARER_TOKEN_REGEX'")
                    return
                }
                else it
            }.let { accessToken -> accessTokenEncoder.decodeToUserId(accessToken).toString() }
            .let { userIdString -> userDetailsService.loadUserByUsername(userIdString) }
            .let { userDetails ->
                UsernamePasswordAuthenticationToken(
                    userDetails,
                    "",
                    userDetails.authorities
                )
            }
        SecurityContextHolder.getContext().authentication = authentication
        return filterChain.doFilter(request, response)
    }
}