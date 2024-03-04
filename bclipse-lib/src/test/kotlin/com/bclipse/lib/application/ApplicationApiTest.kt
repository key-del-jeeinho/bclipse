package com.bclipse.lib.application

import com.bclipse.lib.application.dto.request.AuthApplicationRequest
import com.bclipse.lib.application.entity.ApplicationSecretSign
import com.bclipse.lib.common.entity.BCryptHash
import com.bclipse.lib.common.entity.Base64UUID
import org.junit.jupiter.api.Test

class ApplicationApiTest {
    @Test
    fun authorize() {
        val baseUrl = "http://localhost:8080"
        val applicationId = Base64UUID.fromEncodedString("base64uuid:v1:s0cEHUijTuO8zplqitWupA")
        val applicationSecret = BCryptHash.of("\$2a\$10\$Ek3TP53ws0CH/nzfG8DsteVlWWMpxD6K9xLmo1qXQ/wlGA8LU6886")
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

        val response = ApplicationApi.authorize(
            baseUrl,
            applicationId.value,
            request
        )

        println(response)
    }
}