package com.bclipse.monolith.application.server.dto

data class CreateServerDto(
    val ownerId: String,
    val name: String,
    val description: String,
)
