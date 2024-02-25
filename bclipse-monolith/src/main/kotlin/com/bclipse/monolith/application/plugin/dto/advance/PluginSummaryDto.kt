package com.bclipse.monolith.application.plugin.dto.advance

import com.bclipse.monolith.application.plugin.dto.PluginDto
import com.bclipse.monolith.application.plugin.entity.PluginVersion

data class PluginSummaryDto(
    val plugin: PluginDto,
    val versionSummary: PluginVersionSummaryDto,
) {
    companion object {
        fun from(
            plugin: PluginDto,
            versions: List<PluginVersion>,
        ): PluginSummaryDto {
            val versionSummary = PluginVersionSummaryDto.from(versions)

            return PluginSummaryDto(
                plugin = plugin,
                versionSummary = versionSummary,
            )
        }
    }
}