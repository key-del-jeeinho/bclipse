package com.bclipse.monolith.infra.security

import com.bclipse.monolith.application.user.UserQueryService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    private val userQueryService: UserQueryService
): UserDetailsService {
    override fun loadUserByUsername(userId: String): UserDetails {
        return runCatching {
            val user = userQueryService.queryById(userId)
            UserDetailsAdapter(user)
        }.getOrElse { cause ->
            throw UsernameNotFoundException("user not found (userId = ${userId})", cause)
        }
    }
}