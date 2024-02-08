package com.bclipse.application.infra.web

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class WebExceptionAdvice {
    @ExceptionHandler(WebException::class)
    fun handle(e: WebException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(e.httpStatus)
            .body(ErrorResponse(e.message?:""))
    }
}