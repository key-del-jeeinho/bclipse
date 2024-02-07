package com.bclipse.application.application.request

import com.bclipse.application.application.dto.AddPluginDto

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