package com.bclipse.monolith.application.plugin.s3

import com.bclipse.monolith.application.plugin.s3.S3ConfigKeys.BUCKET
import com.bclipse.monolith.application.plugin.s3.S3ConfigKeys.DOWNLOAD_URL_EXPIRE_IN_SECOND
import com.bclipse.monolith.application.plugin.s3.S3ConfigKeys.UPLOAD_URL_EXPIRE_IN_SECOND
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest
import java.time.Duration

private const val JAR_MIME_TYPE = "application/java-archive"

@Service
class PluginRepositoryS3Service(
    @Value(BUCKET)
    private val bucket: String,
    @Value(DOWNLOAD_URL_EXPIRE_IN_SECOND)
    downloadSecond: String,
    @Value(UPLOAD_URL_EXPIRE_IN_SECOND)
    uploadSecond: String,
    private val s3PreSigner: S3Presigner,
) {
    private val downloadSecond: Long = downloadSecond.toLong()
    private val uploadSecond: Long = uploadSecond.toLong()

    fun createUploadPreSignedUrl(path: String): String {
        val putObjectRequest = PutObjectRequest.builder()
            .bucket(bucket)
            .key(path)
            .contentType(JAR_MIME_TYPE)
            .build()

        val preSignRequest = PutObjectPresignRequest.builder()
            .signatureDuration(Duration.ofSeconds(uploadSecond))
            .putObjectRequest(putObjectRequest)
            .build()

        return s3PreSigner
            .presignPutObject(preSignRequest)
            .url()
            .toString()
    }

    fun createDownloadPreSignedUrl(path: String): String {
        val getObjectRequest = GetObjectRequest.builder()
            .bucket(bucket)
            .key(path)
            .responseContentType(JAR_MIME_TYPE)
            .build()

        val preSignRequest = GetObjectPresignRequest.builder()
            .signatureDuration(Duration.ofSeconds(downloadSecond))
            .getObjectRequest(getObjectRequest)
            .build()

        return s3PreSigner
            .presignGetObject(preSignRequest)
            .url()
            .toString()
    }
}