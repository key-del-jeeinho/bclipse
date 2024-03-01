package com.bclipse.monolith.application.application.repository

import com.bclipse.monolith.application.application.document.ApplicationAccessTokenDocument
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface ApplicationAccessTokenRepository: MongoRepository<ApplicationAccessTokenDocument, ObjectId> {
    fun findByApplicationId(applicationId: String): ApplicationAccessTokenDocument?
}