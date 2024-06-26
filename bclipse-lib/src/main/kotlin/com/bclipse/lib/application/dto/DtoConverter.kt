package com.bclipse.lib.application.dto

import com.bclipse.lib.application.entity.Application
import com.bclipse.lib.application.entity.ApplicationSetting
import com.bclipse.lib.application.entity.PluginMetadata

object DtoConverter {
    fun Application.toDto() =
        ApplicationDto(
            serverId = serverId,
            applicationId = applicationId,
            applicationSecret = applicationSecret,
            createdAt = createdAt,
            secretUpdateAt = secretUpdatedAt,
            secretExpiredAt = secretExpireAt,
            setting = setting.toDto(),
        )

    fun Application.toDetailDto(): SecuredApplicationDto =
        SecuredApplicationDto(
            id = applicationId,
            serverId = serverId,
            createdAt =  createdAt,
            secretUpdatedAt = secretUpdatedAt,
            secretExpireAt = secretExpireAt,
            setting = setting.toDto(),
        )

    fun ApplicationSetting.toDto(): ApplicationSettingDto =
        ApplicationSettingDto(
            plugins = plugins.values.map { it.toDto() },
            externalApplications = externalApplications,
        )

    fun PluginMetadata.toDto(): PluginMetadataDto =
        PluginMetadataDto(
            pluginId = pluginId,
            hashId = hashId,
        )
}