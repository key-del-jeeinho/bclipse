package com.bclipse.application.user

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
}