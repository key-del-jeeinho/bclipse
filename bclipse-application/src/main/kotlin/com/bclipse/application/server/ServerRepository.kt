package com.bclipse.application.server

import com.bclipse.application.server.entity.Server
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface ServerRepository: MongoRepository<Server, ObjectId> {
    fun findByServerId(serverId: String): Server?
}
