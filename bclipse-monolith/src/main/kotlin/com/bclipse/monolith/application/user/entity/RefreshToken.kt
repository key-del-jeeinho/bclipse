package com.bclipse.monolith.application.user.entity

import nonapi.io.github.classgraph.json.Id
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.ZonedDateTime

@Document
class RefreshToken (
    @Id val id: ObjectId,
    @Indexed(unique = true)
    val token: String,
    val userId: String,
    @Indexed(expireAfterSeconds = 0)
    val expireAt: ZonedDateTime,
)