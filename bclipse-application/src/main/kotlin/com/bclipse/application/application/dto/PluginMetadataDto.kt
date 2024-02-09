package com.bclipse.application.application.dto

import com.bclipse.application.application.entity.PluginMetadata

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
