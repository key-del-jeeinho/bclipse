package com.bclipse.application.user

import com.bclipse.application.user.entity.RefreshToken
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface RefreshTokenRepository: MongoRepository<RefreshToken, ObjectId> {
    fun findByToken(token: String): RefreshToken?
}