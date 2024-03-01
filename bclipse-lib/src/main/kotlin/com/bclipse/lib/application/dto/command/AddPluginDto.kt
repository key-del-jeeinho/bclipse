package com.bclipse.lib.application.dto.command

data class AddPluginDto(
    val requesterId: String,
    val applicationId: String,
    val pluginId: String,
    val version: String,
)
