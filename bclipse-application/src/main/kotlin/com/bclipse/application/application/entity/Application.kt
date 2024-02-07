package com.bclipse.application.application.entity

import com.bclipse.application.common.entity.BCryptHash
import com.bclipse.application.common.entity.Base64UUID
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.ZonedDateTime

@Document
class Application(
    @Id val id: ObjectId,
    @Indexed(unique = true)
    val applicationId: Base64UUID,
    val applicationSecret: BCryptHash,
    val serverId: Base64UUID,
    var createdAt: ZonedDateTime,
    val secretUpdatedAt: ZonedDateTime,
    val secretExpireAt: ZonedDateTime,
    setting: ApplicationSetting,
) {
    var setting = setting
        private set

    fun updateSetting(newSetting: ApplicationSetting) {
        setting = newSetting
    }
}