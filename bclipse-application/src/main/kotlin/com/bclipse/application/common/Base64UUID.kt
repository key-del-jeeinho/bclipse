package com.bclipse.application.common

import java.nio.ByteBuffer
import java.util.Base64
import java.util.UUID

@JvmInline
value class Base64UUID private constructor(val value: String) {
    companion object {
        private const val PREFIX = "base64uuid:v1:" //IETF rfc

        fun generate(): Base64UUID = from(UUID.randomUUID())

        fun fromEncodedString(encodedString: String): Base64UUID {
            val prefixRemoved = encodedString.removePrefix(PREFIX)
            require(
                encodedString.startsWith(PREFIX)
                    && isBase64UUIDFormat(prefixRemoved)
            ) { "올바르지 않은 포맷입니다! - '$encodedString'" }

            return Base64UUID(encodedString)
        }

        private fun from(uuid: UUID): Base64UUID {
            val uuidBytes = ByteBuffer.wrap(ByteArray(16)).apply {
                putLong(uuid.mostSignificantBits)
                putLong(uuid.leastSignificantBits)
            }.array()

            val base64UrlEncodedUUID = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(uuidBytes)

            return Base64UUID(PREFIX + base64UrlEncodedUUID)
        }

        private fun isBase64UUIDFormat(encodedString: String): Boolean =
            Base64.getUrlDecoder().runCatching {
                val uuidBytes = decode(encodedString)
                val restoredUUID = ByteBuffer.wrap(uuidBytes).let {
                    val uuid = UUID(it.long, it.long)
                    require(!it.hasRemaining()) { "decoded byte is not UUID format" }
                    uuid
                }
                val reEncodedString = from(restoredUUID)

                reEncodedString.value == encodedString
            }.getOrElse { false }
    }
}