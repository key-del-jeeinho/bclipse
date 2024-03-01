package com.bclipse.loader

import org.bukkit.configuration.file.FileConfiguration

object PluginLoaderConfig {
    lateinit var applicationId: String
        private set
    lateinit var applicationSecret: String
        private set
    lateinit var applicationUrl: String
        private set

    fun initialize(config: FileConfiguration) {
        val id = config.getString("application.id")
        val secret = config.getString("application.secret")
        val url = config.getString("application.url")
            ?: let{
                config["application.url"] = "http://localhost:8080"
                config.getString("http://localhost:8080")
            }

        requireNotNull(id) { "config.yml에 application.id를 입력해주세요." }
        requireNotNull(secret) { "config.yml에 application.secret을 입력해주세요." }
        requireNotNull(url) { "config.yml에 application.url을 입력해주세요." }

        applicationId = id
        applicationSecret = secret
        applicationUrl = url

    }
}