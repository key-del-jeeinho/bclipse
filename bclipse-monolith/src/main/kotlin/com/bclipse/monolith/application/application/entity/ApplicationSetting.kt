package com.bclipse.monolith.application.application.entity

data class ApplicationSetting (
    val plugins: Map<String, PluginMetadata>,
) {
    companion object {
        val DEFAULT = ApplicationSetting(
            plugins = emptyMap()
        )
    }
}
