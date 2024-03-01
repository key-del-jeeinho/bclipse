package com.bclipse.loader

import org.bukkit.plugin.java.JavaPlugin
import org.koin.core.context.startKoin

class PluginLoaderPlugin: JavaPlugin() {
    override fun onEnable() {
        // Plugin startup logic
        saveDefaultConfig()
        PluginLoaderConfig.initialize(config)
        initializeModules()
    }

    override fun onDisable() {
        // Plugin shutdown logic
        saveConfig()
    }
}

fun initializeModules() {
    startKoin {
        modules(appModule)
    }
}
