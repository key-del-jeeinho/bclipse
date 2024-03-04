package com.bclipse.monolith.infra.security

import com.bclipse.lib.application.dto.ApplicationDto
import com.bclipse.monolith.application.user.dto.UserDto
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsAdapter private constructor(
    val type: AccessType,
    private val user: UserDto?,
    private val application: ApplicationDto?,
): UserDetails {
    constructor(user: UserDto): this(
        type = AccessType.USER,
        user = user,
        application = null,
    )

    constructor(application: ApplicationDto): this(
        type = AccessType.USER,
        user = null,
        application = application,
    )

    val userId: String get() = user?.id
        ?:"@NOT_USER_ACCESS"
    val applicationId: String get() = application
        ?.applicationId?.value
        ?:"@NOT_APPLICATION_ACCESS"

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        mutableListOf()

    override fun getPassword(): String =
        when(type) {
            AccessType.USER -> requireNotNull(user).encodedPassword
            AccessType.APPLICATION -> requireNotNull(application)
                .applicationSecret.value
        }

    override fun getUsername(): String =
        when(type) {
            AccessType.USER -> requireNotNull(user).id
            AccessType.APPLICATION -> requireNotNull(application)
                .applicationId.value
        }

    override fun isAccountNonExpired(): Boolean  = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true
}