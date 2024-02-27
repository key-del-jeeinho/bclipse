package com.bclipse.monolith.application.plugin

import com.bclipse.monolith.application.plugin.entity.PluginVersionUploadRequest
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface PluginVersionUploadRequestRepository : MongoRepository<PluginVersionUploadRequest, ObjectId> {
    fun findAllByUrlExpireAtIsBetween(min: Long, max: Long): List<PluginVersionUploadRequest>
}