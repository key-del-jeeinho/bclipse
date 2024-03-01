package com.bclipse.monolith.application.application.document

import com.bclipse.lib.application.entity.Application
import com.bclipse.lib.common.entity.BCryptHash
import com.bclipse.lib.common.entity.Base64UUID
import com.bclipse.monolith.application.application.document.ApplicationSettingDocument.Companion.toDocument
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.ZonedDateTime

@Document
class ApplicationDocument(
    @Id val id: ObjectId,
    @Indexed(unique = true)
    val applicationId: Base64UUID,
    val applicationSecret: BCryptHash,
    val serverId: Base64UUID,
    var createdAt: ZonedDateTime,
    val secretUpdatedAt: ZonedDateTime,
    val secretExpireAt: ZonedDateTime,
    val setting: ApplicationSettingDocument,
) {
    companion object {
        fun Application.toDocument(): ApplicationDocument =
            ApplicationDocument(
                id = id,
                applicationId = applicationId,
                applicationSecret = applicationSecret,
                serverId = serverId,
                createdAt = createdAt,
                secretUpdatedAt = secretUpdatedAt,
                secretExpireAt = secretExpireAt,
                setting = setting.toDocument(),
            )
    }

    fun toEntity(): Application = Application(
        id = id,
        applicationId = applicationId,
        applicationSecret = applicationSecret,
        serverId = serverId,
        createdAt = createdAt,
        secretUpdatedAt = secretUpdatedAt,
        secretExpireAt = secretExpireAt,
        setting = setting.toEntity(),
    )
}