package com.bclipse.monolith.application.user.dto

data class SignupUserDto(
    val id: String,
    val name: String,
    val rawPassword: String,
)