package com.bclipse.monolith.application.application.entity

import com.bclipse.monolith.common.entity.Base64UUID
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Duration
import java.time.ZonedDateTime

@Document
class ApplicationAccessToken (
    @Id val id: ObjectId,
    @Indexed(unique = true)
    val applicationId: Base64UUID,
    accessToken: Base64UUID,
    expireAt: ZonedDateTime,
    lastRefreshedAt: ZonedDateTime,
) {
    var lastRefreshedAt: ZonedDateTime = lastRefreshedAt
        private set
    var accessToken: Base64UUID = accessToken
        private set
    var expireAt: ZonedDateTime = expireAt
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