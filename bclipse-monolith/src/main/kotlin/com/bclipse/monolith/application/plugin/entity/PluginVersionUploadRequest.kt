package com.bclipse.monolith.application.plugin.entity

import com.bclipse.monolith.application.plugin.dto.CreatePluginUrlDto
import nonapi.io.github.classgraph.json.Id
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
class PluginVersionUploadRequest(
    @Id val id: ObjectId,
    @Indexed(unique = true)
    val requestId: UUID,
    val requesterId: String,
    val pluginId: String,
    val version: PluginVersion,
    val timestamp: Long,
    urlExpireAt: Long?
) {
    @Indexed
    var urlExpireAt: Long? = urlExpireAt
        private set

    fun updateUrlExpireAt(urlExpireAt: Long) {
        this.urlExpireAt = urlExpireAt
    }

    companion object {
        fun from(dto: CreatePluginUrlDto, version: PluginVersion): PluginVersionUploadRequest =
            PluginVersionUploadRequest(
                id = ObjectId(),
                requestId = UUID.randomUUID(),
                requesterId = dto.requesterId,
                pluginId = dto.pluginId,
                version = version,
                timestamp = System.currentTimeMillis(),
                urlExpireAt = null,
            )
    }
}