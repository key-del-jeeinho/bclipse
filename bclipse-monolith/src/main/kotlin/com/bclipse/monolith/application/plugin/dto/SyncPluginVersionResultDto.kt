package com.bclipse.monolith.application.plugin.dto

import com.bclipse.monolith.application.plugin.entity.PluginVersionUploadRequest

data class SyncPluginVersionResultDto(
    val searchedPluginCount: Int,
    val searchedPluginVersionCount: Int,
    val targetPluginCount: Int,
    val targetPluginVersionCount: Int,
    val updatedPlugins: List<String>,
    val createdPluginVersions: List<String>,
) {
    override fun toString(): String {
        return """
            - searched: (plugin: $searchedPluginCount, pluginVersion: $searchedPluginVersionCount),
            - target: (plugin: $targetPluginCount, pluginVersion: $targetPluginVersionCount),
            - updatedPlugins: ${updatedPlugins.joinToString(
                prefix = "'", postfix = "'"
            )}
            - createdPluginVersions: ${createdPluginVersions.joinToString(
                prefix = "'", postfix = "'"
            )}
        """.trimIndent()
    }

    companion object {
        fun empty(): SyncPluginVersionResultDto = SyncPluginVersionResultDto(
            searchedPluginCount = 0,
            searchedPluginVersionCount = 0,
            targetPluginCount = 0,
            targetPluginVersionCount = 0,
            updatedPlugins = emptyList(),
            createdPluginVersions = emptyList(),
        )

        fun from(
            searched: List<PluginVersionUploadRequest>,
            target: List<PluginVersionUploadRequest>,
            created: List<PluginVersionUploadRequest>
        ): SyncPluginVersionResultDto {
            val searchedPluginCount = searched.distinctBy { it.pluginId }.size
            val searchedPluginVersionCount = searched.distinctBy {
                it.version.getHashId(it.pluginId)
            }.size

            val targetPluginCount = target.distinctBy { it.pluginId }.size
            val targetPluginVersionCount = target.distinctBy {
                it.version.getHashId(it.pluginId)
            }.size

            val updatedPlugins = created.map { it.pluginId }.distinct()
            val createdVersionsWithPlugin = created.map {
                it.version.toStringWithPluginId(it.pluginId)
            }.distinct()

            return SyncPluginVersionResultDto(
                searchedPluginCount = searchedPluginCount,
                searchedPluginVersionCount = searchedPluginVersionCount,
                targetPluginCount = targetPluginCount,
                targetPluginVersionCount = targetPluginVersionCount,
                updatedPlugins = updatedPlugins,
                createdPluginVersions = createdVersionsWithPlugin,
            )
        }
    }
}
