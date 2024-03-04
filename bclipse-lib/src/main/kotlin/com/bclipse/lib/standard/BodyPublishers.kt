package com.bclipse.lib.standard

import java.net.http.HttpRequest

object BodyPublishers {
    fun ofJson(body: Any): HttpRequest.BodyPublisher {
        val bodyString = ObjectMapper.writeValueAsString(body)
        return HttpRequest.BodyPublishers.ofString(bodyString)
    }
}