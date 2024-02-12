package com.bclipse.monolith.application.plugin.dto

import java.time.ZonedDateTime

data class PluginUrlDto(
    val url: String,
    val pluginId: String,
    val version: String,
    val expireAt: ZonedDateTime,
)
