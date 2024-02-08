package com.bclipse.application.user

import com.bclipse.application.infra.web.WebPrecondition.preconditionWeb
import com.bclipse.application.user.dto.SecuredUserDto
import com.bclipse.application.user.dto.SecuredUserDto.Companion.toProfileDto
import com.bclipse.application.user.dto.SecuredUserDto.Companion.toSecuredDto
import com.bclipse.application.user.dto.UserProfileDto
import com.bclipse.application.user.entity.User
import com.bclipse.application.user.repository.UserRepository
import com.bclipse.application.user.util.DefaultUser
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class UserQueryService(
    private val userRepository: UserRepository,
) {
    fun existsById(userId: String): Boolean {
        //TODO 추후 user 관련 로직 고도화 후 수정예정입니다.
        if(userId == DefaultUser.userId) return true
        return userRepository.existsByUserId(userId)
    }

    fun querySecuredById(userId: String): SecuredUserDto {
        //TODO 추후 user 관련 로직 고도화 후 수정예정입니다.
        if(userId == DefaultUser.userId) return DefaultUser.toSecuredDto()
        return queryById(userId).toSecuredDto()
    }

    fun queryProfileById(userId: String): UserProfileDto {
        //TODO 추후 user 관련 로직 고도화 후 수정예정입니다.
        if(userId == DefaultUser.userId) return DefaultUser. toProfileDto()
        return queryById(userId).toProfileDto()
    }

    private fun queryById(userId: String): User {
        val user = userRepository.findByUserId(userId)

        preconditionWeb(user != null, HttpStatus.NOT_FOUND) { IllegalStateException("유저를 찾을 수 없습니다 - '$userId'") }
        return user
    }
}