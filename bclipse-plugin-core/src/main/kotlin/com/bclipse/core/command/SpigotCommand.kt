package com.bclipse.core.command

import com.bclipse.core.command.context.CommandContext
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

abstract class SpigotCommand(
    children: List<ChildCommand>,
): BclipseCommand(children), CommandExecutor {
    final override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>,
    ): Boolean {
        val context = CommandContext(sender, command, label, args.toList())
        return this.delegateOrExecute(context)
    }
}