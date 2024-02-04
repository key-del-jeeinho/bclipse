package com.bclipse.application.user

import com.bclipse.application.user.dto.SecuredUserDto
import com.bclipse.application.user.dto.SecuredUserDto.Companion.toSecuredDto
import com.bclipse.application.user.dto.SignupUserDto
import com.bclipse.application.user.entity.User
import org.bson.types.ObjectId
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    fun signup(request: SignupUserDto): SecuredUserDto {
        val isUsingId = userRepository.existsByUserId(request.id)
        require(!isUsingId) { "이미 사용되고있는 유저 ID입니다. - '${request.id}'" }

        val encodedPassword = passwordEncoder.encode(request.rawPassword)

        val toCreate = User(
            id = ObjectId(),
            userId = request.id,
            name = request.name,
            encodedPassword = encodedPassword,
            createdAt = ZonedDateTime.now(),
        )

        val user = userRepository.save(toCreate)
        return user.toSecuredDto()
    }
}
