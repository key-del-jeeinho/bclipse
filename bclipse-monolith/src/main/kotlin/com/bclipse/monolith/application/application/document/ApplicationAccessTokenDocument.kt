package com.bclipse.monolith.application.application.document

import com.bclipse.lib.application.entity.ApplicationAccessToken
import com.bclipse.lib.common.entity.Base64UUID
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.ZonedDateTime

@Document
class ApplicationAccessTokenDocument(
    @Id val id: ObjectId,
    @Indexed(unique = true)
    val applicationId: Base64UUID,
    val accessToken: Base64UUID,
    val expireAt: ZonedDateTime,
    val lastRefreshedAt: ZonedDateTime,
) {
    companion object {
        fun ApplicationAccessToken.toDocument(): ApplicationAccessTokenDocument =
            ApplicationAccessTokenDocument(
                id = id,
                applicationId = applicationId,
                accessToken = accessToken,
                expireAt = expireAt,
                lastRefreshedAt = lastRefreshedAt,
            )
    }

    fun toEntity(): ApplicationAccessToken = ApplicationAccessToken(
        id = id,
        applicationId = applicationId,
        accessToken = accessToken,
        expireAt = expireAt,
        lastRefreshedAt = lastRefreshedAt,
    )
}