package com.bclipse.lib.application.dto.request

import com.bclipse.lib.application.dto.command.AddPluginDto

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