package com.bclipse.application.infra.web

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.web.filter.OncePerRequestFilter

class ExceptionHandlerFilter: OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: WebException) {
            response.status = e.httpStatus.value()
            response.contentType = MediaType.APPLICATION_JSON_VALUE
            val errorMessage = e.message ?: e.cause?.message ?: "알 수 없는 오류가 발생하였습니다."
            val errorResponse = ErrorResponse(errorMessage)
            try { //TODO 굳이 try catch 로 오류를 잡아야할지 고민해보기
                val bodyAsString = jacksonObjectMapper().writeValueAsString(errorResponse)
                response.contentType = "application/json"
                response.status = HttpServletResponse.SC_FORBIDDEN
                response.writer.write(bodyAsString)
            } catch (e: Throwable) {
                e.printStackTrace() //TODO 로거사용
                response.contentType = "application/text"
                response.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
                response.writer.write("serialization error!")
            }
        }
    }
}