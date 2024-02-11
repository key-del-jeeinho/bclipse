package com.bclipse.monolith.application.user.repository

import com.bclipse.monolith.application.user.entity.User
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository: MongoRepository<User, ObjectId> {
    fun existsByUserId(userId: String): Boolean
    fun findByUserId(userId: String): User?
}
