package com.bclipse.lib.application

import com.bclipse.lib.application.dto.SimpleApplicationAccessTokenDto
import com.bclipse.lib.application.dto.request.AuthApplicationRequest
import com.bclipse.lib.common.error.ApiException
import com.bclipse.lib.standard.BodyPublishers
import com.bclipse.lib.standard.ObjectMapper
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

object ApplicationApi {
    fun authorize(
        baseUrl: String,
        applicationId: String,
        request: AuthApplicationRequest,
    ): SimpleApplicationAccessTokenDto {
        fun applicationApiURI(path: String)= URI("$baseUrl/api/v1/applications$path")

        val client = HttpClient.newHttpClient()
        val httpRequest = HttpRequest.newBuilder()
            .uri(applicationApiURI("/$applicationId/auth-token"))
            .header(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8")
            .POST(BodyPublishers.ofJson(request))
            .build()
        val response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString())

        if(response.statusCode() != HttpStatus.OK.value())
            throw ApiException(response)

        return ObjectMapper.readValue(
            response.body(),
            SimpleApplicationAccessTokenDto::class.java,
        )
    }
}