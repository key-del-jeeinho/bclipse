package com.bclipse.monolith.application.plugin

import org.springframework.stereotype.Service

@Service
class PluginQueryService(
    private val pluginVersionRepository: PluginVersionRepository,
) {
    fun existsByHashId(hashId: String): Boolean =
        pluginVersionRepository.existsByHashId(hashId)
}