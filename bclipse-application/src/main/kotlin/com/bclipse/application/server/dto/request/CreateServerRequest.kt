package com.bclipse.application.server.dto.request

import com.bclipse.application.server.dto.CreateServerDto

data class CreateServerRequest(
    val name: String,
    val description: String,
) {
    fun toDto(ownerId: String): CreateServerDto = CreateServerDto(
        ownerId = ownerId,
        name = name,
        description = description,
    )
}