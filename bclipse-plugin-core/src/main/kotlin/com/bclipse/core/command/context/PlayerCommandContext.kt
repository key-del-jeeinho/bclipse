package com.bclipse.core.command.context

import org.bukkit.command.Command
import org.bukkit.entity.Player

data class PlayerCommandContext(
    val player: Player,
    val command: Command,
    val label: String,
    val args: List<String>,
)