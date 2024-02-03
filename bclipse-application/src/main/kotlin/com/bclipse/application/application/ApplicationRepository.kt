package com.bclipse.application.application

import com.bclipse.application.application.entity.Application
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface ApplicationRepository: MongoRepository<Application, ObjectId> {
    fun findByApplicationId(applicationId: String): Application?
}
