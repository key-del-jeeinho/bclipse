package com.bclipse.monolith.application.plugin.dto

data class CreatePluginDto(
    val pluginId: String,
    val name: String,
    val description: String,
)