package com.bclipse.monolith.application.server.entity

import com.bclipse.monolith.common.entity.Base64UUID
import nonapi.io.github.classgraph.json.Id
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.ZonedDateTime

@Document
class Server(
    @Id val id: ObjectId,
    @Indexed(unique = true)
    val serverId: Base64UUID,
    @Indexed
    val ownerId: String,
    val name: String,
    val description: String,
    val createdAt: ZonedDateTime,
)
