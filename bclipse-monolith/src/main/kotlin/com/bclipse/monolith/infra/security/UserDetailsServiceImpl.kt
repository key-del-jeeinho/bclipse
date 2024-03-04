package com.bclipse.monolith.infra.security

import com.bclipse.monolith.application.application.ApplicationQueryService
import com.bclipse.monolith.application.user.UserQueryService
import com.bclipse.monolith.infra.security.AccessType.APPLICATION
import com.bclipse.monolith.infra.security.AccessType.USER
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    private val userQueryService: UserQueryService,
    private val applicationQueryService: ApplicationQueryService,
): UserDetailsService {
    companion object {
        const val ACCESS_TYPE_DELIMITER = ":"

        fun getUsernameByUserId(userId: String) =
            "$USER$ACCESS_TYPE_DELIMITER$userId"
        fun getUsernameByApplicationId(applicationId: String) =
            "$APPLICATION$ACCESS_TYPE_DELIMITER$applicationId"
    }

    override fun loadUserByUsername(username: String): UserDetails {
        fun onMalformed(): Nothing = throw UsernameNotFoundException("malformed username - $username")
        val data = username.split(ACCESS_TYPE_DELIMITER)

        if(data.size != 2) onMalformed()
        val type = AccessType.of(data[0]) ?: onMalformed()
        val id = data[1]

        return when(type) {
            USER -> loadUserByUserId(id)
            APPLICATION -> loadUserByApplicationId(id)
        }
    }

    private fun loadUserByUserId(userId: String): UserDetails =
        runCatching {
            val user = userQueryService.queryById(userId)
            UserDetailsAdapter(user)
        }.getOrElse { cause ->
            throw UsernameNotFoundException("user not found (userId = ${userId})", cause)
        }

    private fun loadUserByApplicationId(applicationId: String): UserDetails =
        runCatching {
            val application = applicationQueryService.queryById(applicationId)
            UserDetailsAdapter(application)
        }.getOrElse { cause ->
            throw UsernameNotFoundException("application not found (applicationId = ${applicationId})", cause)
        }
}
