package com.bclipse.monolith.application.application

import com.bclipse.monolith.application.application.dto.AddPluginDto
import com.bclipse.monolith.application.application.dto.UnsecuredApplicationDto
import com.bclipse.monolith.application.application.dto.UnsecuredApplicationDto.Companion.toUnsecuredDto
import com.bclipse.monolith.application.application.entity.PluginMetadata
import com.bclipse.monolith.application.application.repository.ApplicationRepository
import com.bclipse.monolith.infra.web.WebPrecondition
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ApplicationSettingService(
    private val applicationRepository: ApplicationRepository,
) {
    fun addPlugin(dto: AddPluginDto): UnsecuredApplicationDto {
        val application = applicationRepository.findByApplicationId(dto.applicationId)
        WebPrecondition.requireRequest(application != null) { "applicationId가 잘못되었습니다." }

        val plugins = application.setting.plugins.toMutableMap()
        plugins[dto.pluginId] = PluginMetadata(
            pluginId = dto.pluginId,
            version = dto.version,
        )

        val newSetting = application.setting.copy(plugins = plugins)
        application.updateSetting(newSetting)

        val result = applicationRepository.save(application)
        return result.toUnsecuredDto()
    }
}

