package com.bclipse.monolith.application.plugin.entity.document

import com.bclipse.monolith.application.plugin.entity.PluginVersion
import com.bclipse.monolith.application.plugin.entity.VersionType
import nonapi.io.github.classgraph.json.Id
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document
class PluginVersionDocument(
    @Id val id: ObjectId,
    @Indexed(unique = true)
    val hashId: String,
    @Indexed
    val pluginId: String,
    val majorDigit: Int,
    val minorDigit: Int,
    val patchDigit: Int,
    val type: VersionType,
    val fixDigit: Int,
) {
    companion object {
        fun PluginVersion.toDocument(pluginId: String): PluginVersionDocument =
            PluginVersionDocument(
                id = ObjectId(),
                hashId = getHashId(pluginId),
                pluginId = pluginId,
                majorDigit = majorDigit,
                minorDigit = minorDigit,
                patchDigit = patchDigit,
                type = type,
                fixDigit = fixDigit,
            )
    }

    fun toEntity(): PluginVersion =
        PluginVersion(
            majorDigit = majorDigit,
            minorDigit = minorDigit,
            patchDigit = patchDigit,
            type = type,
            fixDigit = fixDigit,
        )
}