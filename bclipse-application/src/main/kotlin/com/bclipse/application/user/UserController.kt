package com.bclipse.application.user

import com.bclipse.application.user.dto.LoginUserDto
import com.bclipse.application.user.dto.SecuredUserDto
import com.bclipse.application.user.dto.SignupUserDto
import com.bclipse.application.user.response.AccessTokenResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "User API")
@RequestMapping("/api/v1/user")
class UserController(
    private val userService: UserService,
    @Value("\${server.domain}")
    private val serverDomain: String,
) {
    @Operation(summary = "유저 회원가입")
    @PostMapping("/signup")
    fun signup(
        @RequestBody request: SignupUserDto,
    ): ResponseEntity<SecuredUserDto> {
        val result = userService.signup(request)
        return ResponseEntity.ok(result)
    }

    @Operation(summary = "유저 로그인")
    @PostMapping("/login")
    fun login(
        @RequestBody request: LoginUserDto,
        response: HttpServletResponse,
    ): ResponseEntity<AccessTokenResponse> {
        val result = userService.login(request)
        val refreshToken = result.refreshToken

        val cookie = ResponseCookie
            .from("refreshToken", refreshToken.refreshToken.value)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(refreshToken.expireInSecond)
            .domain(serverDomain)
            .build()

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(AccessTokenResponse(result.accessToken))
    }
}