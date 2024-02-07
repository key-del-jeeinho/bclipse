package com.bclipse.application.application.entity

import com.bclipse.application.common.entity.BCryptHash
import com.bclipse.application.common.entity.Base64UUID
import org.springframework.security.crypto.bcrypt.BCrypt

@JvmInline
value class ApplicationSecretSign private constructor(
    val value: String
) {
    companion object {
        fun generate(
            applicationId: Base64UUID,
            applicationSecret: BCryptHash,
            timestamp: Long,
        ): ApplicationSecretSign {
            val password = "${applicationId}_$timestamp"
            val salt = applicationSecret.value
            val hash = BCrypt.hashpw(password, salt)

            return ApplicationSecretSign(hash)
        }
    }
}