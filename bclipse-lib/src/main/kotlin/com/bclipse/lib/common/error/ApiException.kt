package com.bclipse.lib.common.error

import java.net.http.HttpResponse

data class ApiException(
    val response: HttpResponse<String>
): RuntimeException("api 호출중 오류가 발생하였습니다! - ${response.body()}")