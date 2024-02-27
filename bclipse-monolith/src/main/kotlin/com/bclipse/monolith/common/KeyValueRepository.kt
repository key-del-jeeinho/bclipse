package com.bclipse.monolith.common

import com.bclipse.monolith.common.document.KeyValueDocument
import org.springframework.data.mongodb.repository.MongoRepository

interface KeyValueRepository: MongoRepository<KeyValueDocument, String> {
    fun findByKey(key: String): KeyValueDocument?
}