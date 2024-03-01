package com.bclipse.monolith.application.user.dto.command

data class LoginUserDto(
    val id: String,
    val rawPassword: String,
)
