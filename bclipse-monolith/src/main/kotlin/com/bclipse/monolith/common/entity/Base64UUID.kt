package com.bclipse.monolith.common.entity

import com.bclipse.monolith.infra.kotlin.LazyPrecondition.require
import java.nio.ByteBuffer
import java.util.*

@JvmInline
value class Base64UUID private constructor(val value: String) {
    companion object {
        private const val PREFIX = "base64uuid:v1:" //IETF rfc

        fun generate(): Base64UUID = from(UUID.randomUUID())

        fun fromEncodedString(base64UUID: String): Base64UUID {
            val lazyMessage = lazy { "올바르지 않은 포맷입니다! - '$base64UUID'" }

            require(base64UUID.startsWith(PREFIX), lazyMessage)
            val encodedString = base64UUID.removePrefix(PREFIX)
            requireBase64UUIDFormat(encodedString, lazyMessage)

            return Base64UUID(base64UUID)
        }

        private fun requireBase64UUIDFormat(encodedString: String, lazyMessage: Lazy<String>) {
            val decoder = Base64.getUrlDecoder()
            try {
                val uuidBytes = decoder.decode(encodedString)
                val restoredUUID = ByteBuffer.wrap(uuidBytes).let {
                    val uuid = UUID(it.long, it.long)
                    require(!it.hasRemaining()) { "decoded byte is not UUID format" }
                    uuid
                }
                val reEncodedString = encode(restoredUUID)
                require(reEncodedString == encodedString) {
                    "re encoded string is not same - '$encodedString' '${reEncodedString}'"
                }
            } catch(throwable: Throwable) {
                throw IllegalArgumentException(lazyMessage.value, throwable)
            }
        }

        private fun from(uuid: UUID): Base64UUID = uuid.let(::encode).let(::addPrefix)

        private fun encode(uuid: UUID): String {
            val uuidBytes = ByteBuffer.wrap(ByteArray(16)).apply {
                putLong(uuid.mostSignificantBits)
                putLong(uuid.leastSignificantBits)
            }.array()

            val base64UrlEncodedUUID = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(uuidBytes)

            return base64UrlEncodedUUID
        }

        private fun addPrefix(encodedString: String) =  Base64UUID(PREFIX + encodedString)
    }
}