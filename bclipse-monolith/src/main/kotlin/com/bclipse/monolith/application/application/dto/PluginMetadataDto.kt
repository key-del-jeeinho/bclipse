package com.bclipse.monolith.application.application.dto

import com.bclipse.monolith.application.application.entity.PluginMetadata

data class PluginMetadataDto(
    val pluginId: String,
    val version: String,
) {
    companion object {
        fun PluginMetadata.toDto(): PluginMetadataDto =
            PluginMetadataDto(
                pluginId = pluginId,
                version = version,
            )
    }
}
