package com.bclipse.monolith.application.plugin

import com.bclipse.monolith.application.plugin.dto.PluginDto.Companion.toDto
import com.bclipse.monolith.application.plugin.dto.advance.PluginDetailDto
import com.bclipse.monolith.application.plugin.dto.advance.PluginSummaryDto
import com.bclipse.monolith.application.plugin.entity.document.PluginVersionDocument
import com.bclipse.monolith.infra.web.WebPrecondition
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class PluginQueryService(
    private val pluginVersionRepository: PluginVersionRepository,
    private val pluginRepository: PluginRepository,
) {
    fun queryAll(): List<PluginSummaryDto> {
        val plugins = pluginRepository.findAll().map { it.toDto() }
        val pluginVersionsByPluginId = pluginVersionRepository.findAll()
            .groupBy(PluginVersionDocument::pluginId)

        val list = plugins.map { plugin ->
            val versions = pluginVersionsByPluginId[plugin.pluginId]
                ?.map(PluginVersionDocument::toEntity)
                ?: emptyList()
            PluginSummaryDto.from(plugin, versions)
        }

        return list
    }

    fun queryById(pluginId: String): PluginDetailDto {
        val plugin = pluginRepository.findByPluginId(pluginId)?.toDto()
        WebPrecondition.preconditionWeb(plugin != null, HttpStatus.NOT_FOUND) {
            IllegalStateException("plugin을 찾을 수 없습니다. - '$pluginId''")
        }

        val versions = pluginVersionRepository.findAllByPluginId(pluginId)
            .map(PluginVersionDocument::toEntity)

        return PluginDetailDto.from(
            plugin = plugin,
            versions = versions,
        )
    }

    fun existsByHashId(hashId: String): Boolean =
        pluginVersionRepository.existsByHashId(hashId)
}