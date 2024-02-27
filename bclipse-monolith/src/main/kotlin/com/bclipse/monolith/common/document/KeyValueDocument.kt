package com.bclipse.monolith.common.document

import nonapi.io.github.classgraph.json.Id
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document
class KeyValueDocument (
    @Id val id: ObjectId,
    @Indexed(unique = true)
    val key: String,
    val value: String,
)