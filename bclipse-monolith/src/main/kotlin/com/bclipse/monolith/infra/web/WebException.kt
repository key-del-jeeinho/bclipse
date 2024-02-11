package com.bclipse.monolith.infra.web

import org.springframework.http.HttpStatus

class WebException private constructor(
    message: String?,
    cause: Throwable?,
    val httpStatus: HttpStatus,
): RuntimeException(message, cause) {
    constructor(httpStatus: HttpStatus, cause: Throwable): this(cause.message, cause, httpStatus)
    constructor(httpStatus: HttpStatus, message: String): this(message, null, httpStatus)
    constructor(httpStatus: HttpStatus, message: String, cause: Throwable): this(message, cause, httpStatus)
}