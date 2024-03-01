package com.bclipse.monolith.application.application

import com.bclipse.lib.application.dto.ApplicationDetailDto
import com.bclipse.lib.application.dto.ApplicationIdDto.Companion.toIdDto
import com.bclipse.lib.application.dto.DtoConverter.toDetailDto
import com.bclipse.lib.application.dto.DtoConverter.toUnsecuredDto
import com.bclipse.lib.application.dto.query.ApplicationAggregateType
import com.bclipse.lib.application.dto.query.ApplicationAggregateType.*
import com.bclipse.lib.application.dto.query.ApplicationQueryResultDto
import com.bclipse.lib.application.dto.query.QueryApplicationDto
import com.bclipse.lib.common.entity.Base64UUID
import com.bclipse.monolith.application.application.repository.ApplicationRepository
import com.bclipse.monolith.application.server.ServerQueryService
import com.bclipse.monolith.infra.web.WebException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class ApplicationQueryService(
    private val applicationRepository: ApplicationRepository,
    private val serverQueryService: ServerQueryService,
) {
    fun accessById(dto: QueryApplicationDto): ApplicationDetailDto {
        val exception = lazy {
            WebException(
                httpStatus = HttpStatus.NOT_FOUND,
                message = "어플리케이션을 찾을 수 없습니다. - ${dto.applicationId}"
            )
        }

        val application = applicationRepository.findByApplicationId(
            dto.applicationId
        )?.toEntity() ?: throw exception.value

        val server = serverQueryService.queryById(application.serverId)
        val hasPermission = server?.ownerId == dto.userId
        if(!hasPermission) throw exception.value

        return application.toDetailDto()
    }

    fun existsById(applicationId: String): Boolean =
        applicationRepository.existsByApplicationId(applicationId)

    fun queryAllByIds(
        applicationIds: List<Base64UUID>,
        aggregateType: ApplicationAggregateType
    ): List<ApplicationQueryResultDto> {
        return when(aggregateType) {
            ID -> applicationIds.map { it.toIdDto() }
            UNSECURED_APPLICATION -> {
                val applications = applicationRepository.findAllByApplicationIdIsIn(
                    applicationIds.map(Base64UUID::value)
                ).map { it.toEntity() }
                return applications.map { it.toUnsecuredDto() }
            }
            APPLICATION_DETAIL -> {
                val applications = applicationRepository.findAllByApplicationIdIsIn(
                    applicationIds.map(Base64UUID::value)
                ).map { it.toEntity() }
                return applications.map { it.toDetailDto() }
            }
        }
    }
}