package com.bclipse.application.user

import com.bclipse.application.infra.web.WebPrecondition.requireRequest
import com.bclipse.application.user.dto.LoginTokenDto
import com.bclipse.application.user.dto.LoginUserDto
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
    private val refreshTokenStore: RefreshTokenStore,
    private val accessTokenEncoder: AccessTokenEncoder,
    private val passwordEncoder: PasswordEncoder,
) {
    fun signup(request: SignupUserDto): SecuredUserDto {
        val isNotUsingId = !userRepository.existsByUserId(request.id)
        requireRequest (isNotUsingId) { "이미 사용되고있는 유저 ID입니다. - '${request.id}'" }

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

    fun login(request: LoginUserDto): LoginTokenDto {
        val lazyMessage = lazy { "ID 또는 비밀번호가 맞지 않습니다." }

        val user = userRepository.findByUserId(request.id)
        requireRequest(user != null, lazyMessage)

        val isRightPassword = passwordEncoder.matches(
            request.rawPassword,
            user.encodedPassword
        )
        requireRequest(isRightPassword, lazyMessage)

        val securedUser = user.toSecuredDto()
        val accessToken = accessTokenEncoder.encode(securedUser)
        val refreshToken = refreshTokenStore.generate(request.id)

        return LoginTokenDto(
            accessToken = accessToken,
            refreshToken = refreshToken,
        )
    }
}
