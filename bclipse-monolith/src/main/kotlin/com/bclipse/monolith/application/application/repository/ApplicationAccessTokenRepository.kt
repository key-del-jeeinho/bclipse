package com.bclipse.monolith.application.application.repository

import com.bclipse.monolith.application.application.entity.ApplicationAccessToken
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface ApplicationAccessTokenRepository: MongoRepository<ApplicationAccessToken, ObjectId> {
    fun findByApplicationId(applicationId: String): ApplicationAccessToken?
}