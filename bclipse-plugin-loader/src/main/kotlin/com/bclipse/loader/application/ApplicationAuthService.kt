package com.bclipse.loader.application

import com.bclipse.lib.application.ApplicationApi
import com.bclipse.lib.application.dto.request.AuthApplicationRequest
import com.bclipse.lib.application.entity.ApplicationSecretSign
import com.bclipse.lib.common.entity.BCryptHash
import com.bclipse.lib.common.entity.Base64UUID
import com.bclipse.loader.PluginLoaderConfig
import java.time.ZonedDateTime

class ApplicationAuthService {
    private lateinit var accessToken: String
    private lateinit var expireAt: ZonedDateTime

    fun fetchAuthorize(
        applicationIdString: String,
        applicationSecretString: String,
    ) {
        val applicationId = Base64UUID.fromEncodedString(applicationIdString)
        val applicationSecret = BCryptHash.of(applicationSecretString)
        val timestamp = System.currentTimeMillis()

        val applicationSecretSign = ApplicationSecretSign.generate(
            applicationId = applicationId,
            applicationSecret = applicationSecret,
            timestamp = timestamp,
        )

        val request = AuthApplicationRequest(
            timestamp = timestamp,
            applicationSecretSign = applicationSecretSign.value
        )

        val authorizeToken = ApplicationApi.authorize(
            baseUrl = PluginLoaderConfig.applicationUrl,
            applicationId = applicationId.value,
            request = request,
        )

        this.accessToken = authorizeToken.accessToken
        this.expireAt = authorizeToken.expireAt
    }
}