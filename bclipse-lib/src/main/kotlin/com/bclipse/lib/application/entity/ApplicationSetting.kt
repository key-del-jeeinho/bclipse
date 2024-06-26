package com.bclipse.lib.application.entity

data class ApplicationSetting (
    val plugins: Map<String, PluginMetadata>,
    val externalApplications: Map<ExternalApplicationType, TossApplication>
) {
    companion object {
        val DEFAULT = ApplicationSetting(
            plugins = emptyMap(),
            externalApplications = emptyMap(),
        )
    }
}
