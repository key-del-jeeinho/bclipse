package com.bclipse.application.user

import com.bclipse.application.infra.web.WebPrecondition.preconditionWeb
import com.bclipse.application.user.dto.SecuredUserDto
import com.bclipse.application.user.dto.SecuredUserDto.Companion.toProfileDto
import com.bclipse.application.user.dto.SecuredUserDto.Companion.toSecuredDto
import com.bclipse.application.user.dto.UserDto
import com.bclipse.application.user.dto.UserDto.Companion.toDto
import com.bclipse.application.user.dto.UserProfileDto
import com.bclipse.application.user.entity.User
import com.bclipse.application.user.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class UserQueryService(
    private val userRepository: UserRepository,
) {
    fun existsById(userId: String): Boolean =
        userRepository.existsByUserId(userId)

    fun queryById(userId: String): UserDto =
        queryEntityById(userId).toDto()

    fun querySecuredById(userId: String): SecuredUserDto =
        queryEntityById(userId).toSecuredDto()

    fun queryProfileById(userId: String): UserProfileDto =
        queryEntityById(userId).toProfileDto()

    private fun queryEntityById(userId: String): User {
        val user = userRepository.findByUserId(userId)

        preconditionWeb(user != null, HttpStatus.NOT_FOUND) { IllegalStateException("유저를 찾을 수 없습니다 - '$userId'") }
        return user
    }
}