package com.bclipse.monolith.application.application

import com.bclipse.monolith.application.application.dto.AddPluginDto
import com.bclipse.monolith.application.application.dto.UnsecuredApplicationDto
import com.bclipse.monolith.application.application.dto.UnsecuredApplicationDto.Companion.toUnsecuredDto
import com.bclipse.monolith.application.application.entity.PluginMetadata
import com.bclipse.monolith.application.application.repository.ApplicationRepository
import com.bclipse.monolith.application.plugin.PluginQueryService
import com.bclipse.monolith.application.plugin.entity.PluginVersion
import com.bclipse.monolith.infra.web.WebPrecondition.requireRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ApplicationSettingService(
    private val applicationRepository: ApplicationRepository,
    private val pluginQueryService: PluginQueryService,
) {
    fun addPlugin(dto: AddPluginDto): UnsecuredApplicationDto {
        val application = applicationRepository.findByApplicationId(dto.applicationId)
        requireRequest(application != null) { "applicationId가 잘못되었습니다." }

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
}

