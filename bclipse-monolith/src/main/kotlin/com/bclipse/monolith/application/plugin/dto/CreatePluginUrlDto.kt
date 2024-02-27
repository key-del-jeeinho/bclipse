package com.bclipse.monolith.application.plugin.dto

data class CreatePluginUrlDto(
    val requesterId: String,
    val pluginId: String,
    val version: String
)
