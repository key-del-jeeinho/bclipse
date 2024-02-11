package com.bclipse.monolith.application.plugin.s3

object S3ConfigKeys {
    private const val ROOT = "spring.cloud.aws.s3.plugin-repository"

    const val BUCKET = "\${$ROOT.bucket}"

    const val UPLOAD_URL_EXPIRE_IN_SECOND =
        "\${$ROOT.pre-signed-url.upload.expire-in-second}"

    const val DOWNLOAD_URL_EXPIRE_IN_SECOND =
        "\${$ROOT.pre-signed-url.download.expire-in-second}"
}