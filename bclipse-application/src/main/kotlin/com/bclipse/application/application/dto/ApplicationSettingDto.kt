package com.bclipse.application.application.dto

import com.bclipse.application.application.dto.PluginMetadataDto.Companion.toDto
import com.bclipse.application.application.entity.ApplicationSetting

data class ApplicationSettingDto(
    val plugins: List<PluginMetadataDto>
) {
    companion object {
        fun ApplicationSetting.toDto(): ApplicationSettingDto =
            ApplicationSettingDto(
                plugins = plugins.values.map { it.toDto() },
            )
    }
}