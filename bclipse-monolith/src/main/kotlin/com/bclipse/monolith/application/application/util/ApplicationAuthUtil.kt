package com.bclipse.monolith.application.application.util

import com.bclipse.lib.application.dto.command.AuthApplicationDto
import com.bclipse.lib.application.entity.Application
import com.bclipse.lib.application.entity.ApplicationSecretSign
import com.bclipse.monolith.infra.web.WebPrecondition.requireRequest
import com.bclipse.monolith.infra.web.WebPrecondition.requireState
import java.time.ZonedDateTime

object ApplicationAuthUtil {
    fun requireRequestSecretSign(
        dto: AuthApplicationDto,
        application: Application
    ) {
        val secretSign = ApplicationSecretSign.generate(
            applicationId = application.applicationId,
            applicationSecret = application.applicationSecret,
            timestamp = dto.timestamp,
        )

        val isRightSecretSign = dto.applicationSecretSign == secretSign.value
        requireRequest(isRightSecretSign) { "전자서명이 잘못되었습니다." }
    }

     fun requireStateSecretNotExpired(application: Application) {
         val now = ZonedDateTime.now()
         val isExpired = application.secretExpireAt.isBefore(now)
         requireState(!isExpired) { "어플리케이션의 시크릿 키가 만료되었습니다." }
    }
}