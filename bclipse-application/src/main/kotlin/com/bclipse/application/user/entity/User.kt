package com.bclipse.application.user.entity

import nonapi.io.github.classgraph.json.Id
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.ZonedDateTime

@Document
class User(
    @Id val id: ObjectId,
    @Indexed(unique = true)
    val userId: String,
    val name: String,
    val encodedPassword: String,
    val createdAt: ZonedDateTime,
)