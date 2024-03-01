package com.bclipse.lib.application.entity

import com.bclipse.lib.common.entity.BCryptHash
import com.bclipse.lib.common.entity.Base64UUID
import org.bson.types.ObjectId
import java.time.ZonedDateTime

class Application(
    val id: ObjectId,
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