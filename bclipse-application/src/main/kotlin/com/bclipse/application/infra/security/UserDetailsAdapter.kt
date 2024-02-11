package com.bclipse.application.infra.security

import com.bclipse.application.user.dto.UserDto
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsAdapter(
    private val user: UserDto
): UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        mutableListOf(SimpleGrantedAuthority("USER"))

    override fun getPassword(): String = user.encodedPassword
    override fun getUsername(): String = user.id

    override fun isAccountNonExpired(): Boolean  = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true
}