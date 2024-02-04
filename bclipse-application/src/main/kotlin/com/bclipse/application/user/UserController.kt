package com.bclipse.application.user

import com.bclipse.application.user.dto.SecuredUserDto
import com.bclipse.application.user.dto.SignupUserDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
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
) {
    @Operation(summary = "유저 회원가입")
    @PostMapping("/signup")
    fun signup(
        @RequestBody request: SignupUserDto,
    ): ResponseEntity<SecuredUserDto> {
        val result = userService.signup(request)
        return ResponseEntity.ok(result)
    }
}