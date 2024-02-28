package com.bclipse.monolith.application.application.dto

import com.bclipse.monolith.application.application.dto.PluginMetadataDto.Companion.toDto
import com.bclipse.monolith.application.application.entity.ApplicationSetting
import com.bclipse.monolith.application.application.entity.ExternalApplicationType
import com.bclipse.monolith.application.application.entity.TossApplication

data class ApplicationSettingDto(
    val plugins: List<PluginMetadataDto>,
    val externalApplications: Map<ExternalApplicationType, TossApplication>,
) {
    companion object {
        fun ApplicationSetting.toDto(): ApplicationSettingDto =
            ApplicationSettingDto(
                plugins = plugins.values.map { it.toDto() },
                externalApplications = externalApplications,
            )
    }
}