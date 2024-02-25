package com.bclipse.monolith.application.plugin.dto.advance

import com.bclipse.monolith.application.plugin.dto.PluginDto
import com.bclipse.monolith.application.plugin.entity.PluginVersion

data class PluginDetailDto(
    val plugin: PluginDto,
    val versionSummary: PluginVersionSummaryDto,
    val versions: List<String>,
) {
    companion object {
        fun from(
            plugin: PluginDto,
            versions: List<PluginVersion>,
        ): PluginDetailDto {
            val versionSummary = PluginVersionSummaryDto.from(versions)
            val versionStringList = versions
                .map(PluginVersion::toString)

            return PluginDetailDto(
                plugin = plugin,
                versionSummary = versionSummary,
                versions = versionStringList,
            )
        }
    }
}