package com.bclipse.core.command.context

import org.bukkit.command.Command
import org.bukkit.command.CommandSender

data class CommandContext(
    val sender: CommandSender,
    val command: Command,
    val label: String,
    val args: List<String>,
)