package com.bclipse.application.server.entity

import com.bclipse.application.common.domain.Base64UUID
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
    val name: String,
    val description: String,
    val createdAt: ZonedDateTime,
)
