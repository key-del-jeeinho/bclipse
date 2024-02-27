package com.bclipse.monolith.common

import com.bclipse.monolith.common.document.KeyValueDocument
import com.fasterxml.jackson.databind.ObjectMapper
import org.bson.types.ObjectId
import org.springframework.stereotype.Component

@Component
class KeyValueStore(
    private val keyValueRepository: KeyValueRepository,
    private val objectMapper: ObjectMapper,
) {
    fun get(key: String, companion: Long.Companion): Long? {
        val document = keyValueRepository.findByKey(key)
        if(document == null) return document

        return runCatching {
            objectMapper.readValue(document.value, Long::class.java)
        }.getOrNull()
    }

    fun set(key: String, value: Any) {
        val serialized = objectMapper.writeValueAsString(value)

        val exists = keyValueRepository.findByKey(key)
        val document = KeyValueDocument(
            id = exists?.id ?: ObjectId(),
            key = key,
            value = serialized
        )
        keyValueRepository.save(document)
    }
}
