package com.bclipse.monolith.application.plugin

import com.bclipse.monolith.application.plugin.entity.document.PluginVersionDocument
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface PluginVersionRepository: MongoRepository<PluginVersionDocument, ObjectId> {
    fun existsByHashId(hashId: String): Boolean
    fun deleteByHashId(hashId: String)
}
