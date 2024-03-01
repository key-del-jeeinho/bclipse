package com.bclipse.monolith.application.user

import com.bclipse.monolith.application.user.dto.*
import com.bclipse.monolith.application.user.dto.SecuredUserDto.Companion.toProfileDto
import com.bclipse.monolith.application.user.dto.SecuredUserDto.Companion.toSecuredDto
import com.bclipse.monolith.application.user.dto.query.UserAggregateType.*
import com.bclipse.monolith.application.user.dto.UserDto.Companion.toDto
import com.bclipse.monolith.application.user.dto.UserIdDto.Companion.toIdDto
import com.bclipse.monolith.application.user.dto.query.UserAggregateType
import com.bclipse.monolith.application.user.dto.query.UserQueryResultDto
import com.bclipse.monolith.application.user.entity.User
import com.bclipse.monolith.application.user.repository.UserRepository
import com.bclipse.monolith.infra.web.WebPrecondition.preconditionWeb
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

    fun queryAllByIds(
        userIds: List<String>,
        aggregateType: UserAggregateType
    ): List<UserQueryResultDto> {
        return when(aggregateType) {
            ID -> userIds.map { it.toIdDto() }
            SECURED_USER -> {
                val users = userRepository.findAllByUserIdIsIn(userIds)
                users.map { it.toSecuredDto() }
            }
            USER_PROFILE -> {
                val users = userRepository.findAllByUserIdIsIn(userIds)
                users.map { it.toProfileDto() }
            }
        }
    }
}