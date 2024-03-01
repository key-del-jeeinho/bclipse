package com.bclipse.monolith.application.application.dto.request

import com.bclipse.monolith.application.application.dto.command.AddPluginDto

data class AddPluginRequest(
    val pluginId: String,
    val version: String,
) {
    fun toDto(requesterId: String, applicationId: String): AddPluginDto =
        AddPluginDto(
            requesterId = requesterId,
            applicationId = applicationId,
            pluginId = pluginId,
            version = version,
        )
}