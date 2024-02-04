package com.bclipse.application.user

import com.bclipse.application.user.entity.User
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository: MongoRepository<User, ObjectId> {
    fun existsByUserId(userId: String): Boolean
}