package com.bclipse.monolith.application.application.dto.command

data class AddPluginDto(
    val requesterId: String,
    val applicationId: String,
    val pluginId: String,
    val version: String,
)
