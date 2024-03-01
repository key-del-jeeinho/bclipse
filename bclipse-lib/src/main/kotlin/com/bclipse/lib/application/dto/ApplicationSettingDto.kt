package com.bclipse.lib.application.dto

import com.bclipse.lib.application.entity.ExternalApplicationType
import com.bclipse.lib.application.entity.TossApplication

data class ApplicationSettingDto(
    val plugins: List<PluginMetadataDto>,
    val externalApplications: Map<ExternalApplicationType, TossApplication>,
)