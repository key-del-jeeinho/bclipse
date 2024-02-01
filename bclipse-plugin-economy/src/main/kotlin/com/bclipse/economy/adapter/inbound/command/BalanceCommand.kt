package com.bclipse.economy.adapter.inbound.command

import com.bclipse.core.command.ChildCommand
import com.bclipse.core.command.SpigotCommand
import com.bclipse.core.command.context.CommandContext

class BalanceCommand(
    vararg children: ChildCommand
): SpigotCommand(children.toList()) {
    override fun execute(context: CommandContext): Boolean = false
}