package com.bclipse.application.application

import com.bclipse.application.application.dto.ApplicationDetailDto
import com.bclipse.application.application.dto.ApplicationDetailDto.Companion.toDetailDto
import com.bclipse.application.application.dto.QueryApplicationDto
import com.bclipse.application.application.repository.ApplicationRepository
import com.bclipse.application.infra.web.WebException
import com.bclipse.application.server.ServerQueryService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class ApplicationQueryService(
    private val applicationRepository: ApplicationRepository,
    private val serverQueryService: ServerQueryService,
) {
    fun queryById(dto: QueryApplicationDto): ApplicationDetailDto {
        val exception = lazy {
            WebException(
                httpStatus = HttpStatus.NOT_FOUND,
                message = "어플리케이션을 찾을 수 없습니다. - ${dto.applicationId}"
            )
        }

        val application = applicationRepository.findByApplicationId(
            dto.applicationId
        ) ?: throw exception.value

        val server = serverQueryService.queryById(application.serverId)
        val hasPermission = server?.ownerId == dto.userId
        if(!hasPermission) throw exception.value

        return application.toDetailDto()
    }
}