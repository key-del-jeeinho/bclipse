package com.bclipse.application.common.entity

import org.springframework.security.crypto.bcrypt.BCrypt
import java.util.UUID

@JvmInline
value class BCryptHash private constructor(
    val value: String
){
    companion object {
        fun generate(): BCryptHash {
            val uuid = UUID.randomUUID().toString()
            val secret = BCrypt.hashpw(uuid, BCrypt.gensalt())
            return BCryptHash(secret)
        }
    }
}