package com.bclipse.monolith.application.application.document

import com.bclipse.lib.application.entity.TossApiVersion
import com.bclipse.lib.application.entity.TossApplication
import com.bclipse.lib.common.entity.Base64UUID

data class TossApplicationDocument(
    val id: Base64UUID,
    val clientKey: String,
    val secretKey: String,
    val version: TossApiVersion,
) {
    companion object {
        fun TossApplication.toDocument(): TossApplicationDocument =
            TossApplicationDocument(
                id = id,
                clientKey = clientKey,
                secretKey = secretKey,
                version = version,
            )
    }

    fun toEntity(): TossApplication =
        TossApplication(
            id = id,
            clientKey = clientKey,
            secretKey = secretKey,
            version = version,
        )
}