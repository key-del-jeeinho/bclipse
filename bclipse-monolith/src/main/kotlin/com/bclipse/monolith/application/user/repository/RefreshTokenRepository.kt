package com.bclipse.monolith.application.user.repository

import com.bclipse.monolith.application.user.entity.RefreshToken
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface RefreshTokenRepository: MongoRepository<RefreshToken, ObjectId> {
    fun findByToken(token: String): RefreshToken?
}