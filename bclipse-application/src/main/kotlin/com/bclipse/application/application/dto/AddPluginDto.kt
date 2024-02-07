package com.bclipse.application.application.dto

data class AddPluginDto(
    val requesterId: String,
    val applicationId: String,
    val pluginId: String,
    val version: String,
)
