package com.bclipse.monolith.application.plugin

import com.bclipse.monolith.application.plugin.dto.*
import com.bclipse.monolith.application.plugin.dto.PluginDto.Companion.toDto
import com.bclipse.monolith.application.plugin.entity.Plugin
import com.bclipse.monolith.application.plugin.entity.PluginVersion
import com.bclipse.monolith.application.plugin.entity.PluginVersionUploadRequest
import com.bclipse.monolith.application.plugin.entity.document.PluginVersionDocument.Companion.toDocument
import com.bclipse.monolith.application.plugin.s3.PluginS3Repository
import com.bclipse.monolith.infra.web.WebException
import com.bclipse.monolith.infra.web.WebPrecondition.requireRequest
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class PluginService(
    private val pluginS3Repository: PluginS3Repository,
    private val pluginVersionRepository: PluginVersionRepository,
    private val pluginVersionUploadRequestRepository: PluginVersionUploadRequestRepository,
    private val pluginRepository: PluginRepository,
) {
    fun create(dto: CreatePluginDto): PluginDto {
        val isExistingPlugin = pluginRepository.existsByPluginId(dto.pluginId)
        requireRequest(!isExistingPlugin) { "이미 존재하는 pluginId입니다. - '${dto.pluginId}'" }

        val now = ZonedDateTime.now()

        val toCreate = Plugin(
            id = ObjectId(),
            pluginId = dto.pluginId,
            name = dto.name,
            description = dto.description,
            createdAt = now,
        )

        val plugin = pluginRepository.save(toCreate)
        return plugin.toDto()
    }

    fun createUploadUrl(dto: CreatePluginUrlDto): PluginUrlDto {
        val version = runCatching {
            PluginVersion.from(dto.version)
        }.getOrElse { throwable -> throw WebException(HttpStatus.BAD_REQUEST, throwable) }

        val request = PluginVersionUploadRequest.from(dto, version)
        pluginVersionUploadRequestRepository.save(request)

        checkPluginExists(dto.pluginId, version)
        val result = pluginS3Repository.createUploadPreSignedUrl(dto)

        val urlExpireAt = result.expireAt.toEpochSecond()
        request.updateUrlExpireAt(urlExpireAt)
        pluginVersionUploadRequestRepository.save(request)

        return result
    }

    fun syncVersionFromS3ByUploadRequests(now: Long, lastSyncedAt: Long): SyncPluginVersionResultDto {
        val GAP_MILLIS = 10

        val searched = pluginVersionUploadRequestRepository.findAllByUrlExpireAtIsBetween(
            min = lastSyncedAt - GAP_MILLIS,
            now + GAP_MILLIS,
        )

        if(searched.isEmpty()) return SyncPluginVersionResultDto.empty()

        val target = searched.distinctBy(PluginVersionUploadRequest::version)
            .filter { request ->
                pluginS3Repository.existsByPluginIdAndVersion(
                    request.pluginId,
                    request.version
                )
            }

        val created = target.mapNotNull { request ->
            val result = createNewVersionIfNotExists(request.pluginId, request.version)
            if(result != null) request else null
        }

        return SyncPluginVersionResultDto.from(
            searched = searched,
            target = target,
            created = created,
        )
    }

    fun createDownloadUrl(dto: CreatePluginUrlDto): PluginUrlDto {
        val version = runCatching {
            PluginVersion.from(dto.version)
        }.getOrElse { throwable -> throw WebException(HttpStatus.BAD_REQUEST, throwable) }

        checkPluginExists(dto.pluginId, version)
        return pluginS3Repository.createDownloadPreSignedUrl(dto)
    }

    private fun createNewVersionIfNotExists(pluginId: String, version: PluginVersion): PluginVersion? {
        val hashId = version.getHashId(pluginId)
        val isExists = pluginVersionRepository.existsByHashId(hashId)
        if(isExists) return null

        val toCreate = version.toDocument(pluginId)

        val document = pluginVersionRepository.save(toCreate)
        return document.toEntity()
    }

    private fun checkPluginExists(pluginId: String, version: PluginVersion) {
        val hashId = version.getHashId(pluginId)
        val isExisingVersion = pluginVersionRepository.existsByHashId(hashId)

        val isExistingVersionInS3 = pluginS3Repository.existsByPluginIdAndVersion(pluginId, version)

        if(isExisingVersion && !isExistingVersionInS3)
            pluginVersionRepository.deleteByHashId(hashId)

        requireRequest(!isExistingVersionInS3) { "이미 존재하는 버전입니다. - '$pluginId' '$version'" }
    }
}
