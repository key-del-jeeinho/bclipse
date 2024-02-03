package com.bclipse.application.application

import com.bclipse.application.application.domain.ApplicationSecretSign
import com.bclipse.application.application.dto.AuthApplicationDto
import com.bclipse.application.application.entity.Application
import java.time.ZonedDateTime

object ApplicationAuthUtil {
    fun validateSecretSign(
        dto: AuthApplicationDto,
        application: Application
    ) {
        val secretSign = ApplicationSecretSign.generate(
            applicationId = application.applicationId,
            applicationSecret = application.applicationSecret,
            timestamp = dto.timestamp,
        )

        if(dto.applicationSecretSign != secretSign.value)
            throw RuntimeException("전자서명이 잘못되었습니다.") //TODO
    }

     fun validateSecretNotExpired(application: Application) {
         val now = ZonedDateTime.now()
         val isExpired = application.secretExpireAt.isBefore(now)
         if(isExpired) throw java.lang.RuntimeException("어플리케이션의 시크릿 키가 만료되었습니다.") //TODO
    }
}