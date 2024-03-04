package com.bclipse.monolith.infra.security

import com.bclipse.lib.common.entity.Base64UUID
import com.bclipse.monolith.application.user.util.AccessTokenEncoder
import com.bclipse.monolith.infra.security.UserDetailsServiceImpl.Companion.getUsernameByApplicationId
import com.bclipse.monolith.infra.security.UserDetailsServiceImpl.Companion.getUsernameByUserId
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.filter.OncePerRequestFilter

// Bearer token is need case=insensitive in RFC 6749
//https://datatracker.ietf.org/doc/html/rfc6749#section-5.1
val BEARER_TOKEN_REGEX = Regex("^Bearer (.*)", RegexOption.IGNORE_CASE)

class UserAuthenticationFilter(
    private val userAccessTokenEncoder: AccessTokenEncoder,
    private val userDetailsService: UserDetailsService,
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val type = getAccessType(request) ?: AccessType.USER
        val token = getAccessToken(request) ?: return filterChain.doFilter(request, response)

        val userDetails = when(type) {
            AccessType.USER -> getUserDetailsByUserAccessToken(token)
            AccessType.APPLICATION -> getUserDetailsByApplicationAccessToken(token)
        } ?: return filterChain.doFilter(request, response)

        val authentication = UsernamePasswordAuthenticationToken(
            userDetails,
            "",
            userDetails.authorities
        )
        SecurityContextHolder.getContext().authentication = authentication

        return filterChain.doFilter(request, response)
    }

    private fun getUserDetailsByApplicationAccessToken(token: String): UserDetails {
        val applicationId = Base64UUID.fromEncodedString(token)
        return userDetailsService.loadUserByUsername(
            getUsernameByApplicationId(applicationId.value)
        )
    }

    private fun getUserDetailsByUserAccessToken(token: String): UserDetails? {
        val accessToken = BEARER_TOKEN_REGEX.find(token)?.groupValues?.get(1)

        if(accessToken.isNullOrBlank()) {
            logger.warn("BEARER_TOKEN_REGEX is matched, but token is malformed. please check 'BEARER_TOKEN_REGEX'")
            return null
        }

        val userIdString = userAccessTokenEncoder.decodeToUserId(accessToken)
        return userDetailsService.loadUserByUsername(
            getUsernameByUserId(userIdString)
        )
    }
}

private fun getAccessType(request: HttpServletRequest): AccessType? {
    val typeHeader = request.getHeader("X-Access-Type") ?: return null
    return AccessType.of(typeHeader)

}

private fun getAccessToken(request: HttpServletRequest): String? {
    val token = request.getHeader(HttpHeaders.AUTHORIZATION) ?: return null
    return if(
        token.isEmpty()
        || !token.matches(BEARER_TOKEN_REGEX)
    ) null else token
}