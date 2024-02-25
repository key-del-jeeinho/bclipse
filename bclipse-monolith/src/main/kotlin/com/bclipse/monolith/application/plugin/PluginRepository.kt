package com.bclipse.monolith.application.plugin

import com.bclipse.monolith.application.plugin.entity.Plugin
import org.springframework.data.mongodb.repository.MongoRepository

interface PluginRepository: MongoRepository<Plugin, String> {
    fun existsByPluginId(pluginId: String): Boolean
    fun findByPluginId(pluginId: String): Plugin?
}