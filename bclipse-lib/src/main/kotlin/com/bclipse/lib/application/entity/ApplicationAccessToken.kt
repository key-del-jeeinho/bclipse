package com.bclipse.lib.application.entity

import com.bclipse.lib.common.entity.Base64UUID
import org.bson.types.ObjectId
import java.time.Duration
import java.time.ZonedDateTime

class ApplicationAccessToken (
    val id: ObjectId,
    val applicationId: Base64UUID,
    accessToken: Base64UUID,
    expireAt: ZonedDateTime,
    lastRefreshedAt: ZonedDateTime,
) {
    var accessToken: Base64UUID = accessToken
        private set
    var expireAt: ZonedDateTime = expireAt
        private set
    var lastRefreshedAt: ZonedDateTime = lastRefreshedAt
        private set

    fun refreshAccessToken(
        accessToken: Base64UUID,
        now: ZonedDateTime,
        expireIn: Duration
    ) {
        this.accessToken = accessToken
        this.lastRefreshedAt = now
        this.expireAt = now.plus(expireIn)
    }
}