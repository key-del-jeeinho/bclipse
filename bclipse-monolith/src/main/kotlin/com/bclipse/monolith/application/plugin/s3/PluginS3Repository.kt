package com.bclipse.monolith.application.plugin.s3

import com.bclipse.monolith.application.plugin.dto.CreatePluginUrlDto
import com.bclipse.monolith.application.plugin.dto.PluginUrlDto
import com.bclipse.monolith.application.plugin.entity.PluginVersion
import com.bclipse.monolith.application.plugin.s3.S3ConfigKeys.BUCKET
import com.bclipse.monolith.application.plugin.s3.S3ConfigKeys.DOWNLOAD_URL_EXPIRE_IN_SECOND
import com.bclipse.monolith.application.plugin.s3.S3ConfigKeys.UPLOAD_URL_EXPIRE_IN_SECOND
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import software.amazon.awssdk.awscore.presigner.PresignedRequest
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.HeadObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.model.S3Exception
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest
import java.time.Duration
import java.time.ZoneId

private const val JAR_MIME_TYPE = "application/java-archive"

@Service
class PluginS3Repository(
    @Value(BUCKET)
    private val bucket: String,
    @Value(DOWNLOAD_URL_EXPIRE_IN_SECOND)
    downloadSecond: String,
    @Value(UPLOAD_URL_EXPIRE_IN_SECOND)
    uploadSecond: String,
    private val s3PreSigner: S3Presigner,
    private val s3Client: S3Client,
) {
    private val downloadSecond: Long = downloadSecond.toLong()
    private val uploadSecond: Long = uploadSecond.toLong()

    fun createUploadPreSignedUrl(dto: CreatePluginUrlDto): PluginUrlDto {
        val path = generateS3Path(dto.pluginId, dto.version)

        val putObjectRequest = PutObjectRequest.builder()
            .bucket(bucket)
            .key(path)
            .contentType(JAR_MIME_TYPE)
            .build()

        val preSignRequest = PutObjectPresignRequest.builder()
            .signatureDuration(Duration.ofSeconds(uploadSecond))
            .putObjectRequest(putObjectRequest)
            .build()

        val preSignedRequest = s3PreSigner
            .presignPutObject(preSignRequest)

        return preSignedRequest.toPluginUrlDto(
            pluginId = dto.pluginId,
            version  = dto.version,
        )
    }

    fun createDownloadPreSignedUrl(dto: CreatePluginUrlDto): PluginUrlDto {
        val path = generateS3Path(dto.pluginId, dto.version)

        val getObjectRequest = GetObjectRequest.builder()
            .bucket(bucket)
            .key(path)
            .responseContentType(JAR_MIME_TYPE)
            .build()

        val preSignRequest = GetObjectPresignRequest.builder()
            .signatureDuration(Duration.ofSeconds(downloadSecond))
            .getObjectRequest(getObjectRequest)
            .build()

        val preSignedRequest = s3PreSigner
            .presignGetObject(preSignRequest)

        return preSignedRequest.toPluginUrlDto(
            pluginId = dto.pluginId,
            version  = dto.version,
        )
    }

    fun existsByPluginIdAndVersion(pluginId: String, version: PluginVersion): Boolean {
        val path = generateS3Path(pluginId, version.toString())

        try {
            val headObjectRequest = HeadObjectRequest.builder()
                .bucket(bucket)
                .key(path)
                .build()

            s3Client.headObject(headObjectRequest)
            return true
        } catch(e: S3Exception) {
            if(e.statusCode() == HttpStatus.NOT_FOUND.value()) return false
            else throw e
        }
    }
}

private fun generateS3Path(pluginId: String, versionString: String): String =
    "$pluginId/$versionString.jar"

fun PresignedRequest.toPluginUrlDto(
    pluginId: String,
    version: String,
): PluginUrlDto {
    val url = this.url().toString()
    val expireAt = this.expiration()
        .atZone(ZoneId.of("+09:00"))

    return PluginUrlDto(
        url = url,
        pluginId = pluginId,
        version = version,
        expireAt = expireAt,
    )
}