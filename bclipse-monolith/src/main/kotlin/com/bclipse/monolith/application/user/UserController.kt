package com.bclipse.monolith.application.user

import com.bclipse.monolith.infra.security.UserDetailsAdapter
import com.bclipse.monolith.application.user.dto.*
import com.bclipse.monolith.application.user.dto.response.AccessTokenResponse
import com.bclipse.monolith.application.user.util.RefreshTokenCookieUtil.toHttpOnlySecuredCookie
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@Tag(name = "User API")
@RequestMapping("/api/v1/user")
class UserController(
    private val userService: UserService,
    private val userQueryService: UserQueryService,
    @Value("\${server.domain}")
    private val serverDomain: String,
) {
    @Operation(summary = "내 정보 조회")
    @GetMapping("/my")
    fun my(
        @AuthenticationPrincipal userDetail: UserDetailsAdapter,
    ): ResponseEntity<SecuredUserDto> {
        val result = userQueryService.querySecuredById(userDetail.userId)
        return ResponseEntity.ok(result)
    }

    @Operation(summary = "유저 단건 조회")
    @GetMapping("/{userId}")
    fun queryById(
        @PathVariable userId: String,
    ): ResponseEntity<UserProfileDto> {
        val result = userQueryService.queryProfileById(userId)
        return ResponseEntity.ok(result)
    }

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
    ): ResponseEntity<AccessTokenResponse> {
        val result = userService.login(request)

        val cookie = result.refreshToken.toHttpOnlySecuredCookie(
            domain = serverDomain,
            path = "/",
        )

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(AccessTokenResponse(result.accessToken))
    }

    @Operation(summary = "유저 로그인 갱신")
    @PostMapping("/refresh-login")
    fun login(
        @CookieValue ("refreshToken")
        refreshToken: String
    ): ResponseEntity<AccessTokenResponse> {
        val result = userService.refreshLogin(
            RefreshUserLoginDto(refreshToken)
        )

        val cookie = result.refreshToken.toHttpOnlySecuredCookie(
            domain = serverDomain,
            path = "/",
        )

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(AccessTokenResponse(result.accessToken))
    }
}