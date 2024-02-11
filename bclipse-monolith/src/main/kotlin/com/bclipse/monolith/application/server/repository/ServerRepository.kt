package com.bclipse.monolith.application.server.repository

import com.bclipse.monolith.application.server.entity.Server
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface ServerRepository: MongoRepository<Server, ObjectId> {
    fun findByServerId(serverId: String): Server?
    fun findAllByOwnerId(ownerId: String): List<Server>
}
