package com.bclipse.monolith.application.plugin.entity

import nonapi.io.github.classgraph.json.Id
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.ZonedDateTime

@Document
class Plugin(
    @Id val id: ObjectId,
    @Indexed(unique = true)
    val pluginId: String,
    val name: String,
    val description: String,
    val createdAt: ZonedDateTime,
)
