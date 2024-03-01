package com.bclipse.monolith.application.application.repository

import com.bclipse.monolith.application.application.document.ApplicationDocument
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface ApplicationRepository: MongoRepository<ApplicationDocument, ObjectId> {
    fun existsByApplicationId(applicationId: String): Boolean
    fun findByApplicationId(applicationId: String): ApplicationDocument?
    fun findAllByApplicationIdIsIn(applicationId: List<String>): List<ApplicationDocument>
}
