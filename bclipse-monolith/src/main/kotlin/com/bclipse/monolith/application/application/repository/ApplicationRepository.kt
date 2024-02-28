package com.bclipse.monolith.application.application.repository

import com.bclipse.monolith.application.application.entity.Application
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface ApplicationRepository: MongoRepository<Application, ObjectId> {
    fun existsByApplicationId(applicationId: String): Boolean
    fun findByApplicationId(applicationId: String): Application?
}
