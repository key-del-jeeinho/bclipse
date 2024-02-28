package com.bclipse.monolith.application.application

import com.bclipse.monolith.application.application.dto.AddPluginDto
import com.bclipse.monolith.application.application.dto.AddTossApplicationDto
import com.bclipse.monolith.application.application.dto.UnsecuredApplicationDto
import com.bclipse.monolith.application.application.dto.UnsecuredApplicationDto.Companion.toUnsecuredDto
import com.bclipse.monolith.application.application.entity.Application
import com.bclipse.monolith.application.application.entity.ExternalApplicationType
import com.bclipse.monolith.application.application.entity.PluginMetadata
import com.bclipse.monolith.application.application.entity.TossApplication
import com.bclipse.monolith.application.application.repository.ApplicationRepository
import com.bclipse.monolith.application.plugin.PluginQueryService
import com.bclipse.monolith.application.plugin.entity.PluginVersion
import com.bclipse.monolith.application.server.ServerQueryService
import com.bclipse.monolith.application.toss.TossService
import com.bclipse.monolith.common.entity.Base64UUID
import com.bclipse.monolith.infra.web.WebPrecondition
import com.bclipse.monolith.infra.web.WebPrecondition.requireRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ApplicationSettingService(
    private val applicationRepository: ApplicationRepository,
    private val serverQueryService: ServerQueryService,
    private val pluginQueryService: PluginQueryService,
    private val tossService: TossService
) {
    fun addTossApplication(dto: AddTossApplicationDto): UnsecuredApplicationDto {
        val application = accessApplication(
            applicationId = dto.applicationId,
            userId = dto.requesterId
        )

        tossService.validateApiKey(dto.clientKey, dto.secretKey)

        val externalApplications = application.setting.externalApplications.toMutableMap()
        externalApplications[ExternalApplicationType.TOSS] = TossApplication(
            id = Base64UUID.generate(),
            clientKey = dto.clientKey,
            secretKey = dto.secretKey,
            version = dto.version,
        )

        val setting = application.setting.copy(externalApplications = externalApplications)
        application.updateSetting(setting)

        val result = applicationRepository.save(application)
        return result.toUnsecuredDto()
    }

    fun addPlugin(dto: AddPluginDto): UnsecuredApplicationDto {
        val application = accessApplication(
            applicationId = dto.applicationId,
            userId = dto.requesterId
        )

        val version = PluginVersion.from(dto.version)
        val hashId = version.getHashId(dto.pluginId)

        val pluginExists = pluginQueryService.existsByHashId(hashId)
        requireRequest(pluginExists) { "version이 잘못되었습니다." }

        val plugins = application.setting.plugins.toMutableMap()
        plugins[dto.pluginId] = PluginMetadata(
            pluginId = dto.pluginId,
            hashId = hashId,
        )

        val newSetting = application.setting.copy(plugins = plugins)
        application.updateSetting(newSetting)

        val result = applicationRepository.save(application)
        return result.toUnsecuredDto()
    }

    private fun accessApplication(applicationId: String, userId: String): Application {
        val application = applicationRepository.findByApplicationId(applicationId)
        requireRequest(application != null) { "applicationId가 잘못되었습니다." }

        val server = serverQueryService.queryById(application.serverId)
        WebPrecondition.checkState(server != null) { "serverId가 잘못되었습니다. - '${application.serverId}'" }

        val isOwner = userId == server.ownerId
        WebPrecondition.requirePermission(isOwner) { "작업을 요청할 권한이 없습니다. - '${userId}'" }

        return application
    }
}

