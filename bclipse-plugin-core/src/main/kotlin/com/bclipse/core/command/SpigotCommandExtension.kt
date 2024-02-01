package com.bclipse.core.command

import org.bukkit.command.CommandExecutor
import org.bukkit.plugin.java.JavaPlugin

object SpigotCommandExtension {
    fun JavaPlugin.command(
        vararg aliases: String,
        executorFactory: () -> CommandExecutor
    ) {
        aliases.map { alias ->
            requireNotNull(this.getCommand(alias))
                .setExecutor(executorFactory())
        }
    }
}