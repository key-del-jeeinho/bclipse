package com.bclipse.monolith.application.plugin.dto

import com.bclipse.monolith.application.plugin.entity.Plugin
import java.time.ZonedDateTime

data class PluginDto(
    val pluginId: String,
    val name: String,
    val description: String,
    val createdAt: ZonedDateTime,
) {
    companion object {
        fun Plugin.toDto(): PluginDto =
            PluginDto(
                pluginId = pluginId,
                name = name,
                description = description,
                createdAt = createdAt,
            )
    }
}