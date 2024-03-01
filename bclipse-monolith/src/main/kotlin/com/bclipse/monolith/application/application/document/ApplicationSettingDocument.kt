package com.bclipse.monolith.application.application.document

import com.bclipse.lib.application.entity.ApplicationSetting
import com.bclipse.lib.application.entity.ExternalApplicationType
import com.bclipse.lib.application.entity.PluginMetadata
import com.bclipse.monolith.application.application.document.TossApplicationDocument.Companion.toDocument

data class ApplicationSettingDocument (
    val plugins: Map<String, PluginMetadata>,
    val externalApplications: Map<ExternalApplicationType, TossApplicationDocument>
) {
    companion object {
        fun ApplicationSetting.toDocument(): ApplicationSettingDocument =
            ApplicationSettingDocument(
                plugins = plugins,
                externalApplications = externalApplications
                    .mapValues { it.value.toDocument() },
            )
    }

    fun toEntity(): ApplicationSetting = ApplicationSetting(
        plugins = plugins,
        externalApplications = externalApplications
            .mapValues { it.value.toEntity() },
    )
}
