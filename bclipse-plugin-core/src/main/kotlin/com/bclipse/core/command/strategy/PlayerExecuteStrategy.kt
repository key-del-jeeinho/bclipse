package com.bclipse.core.command.strategy

import com.bclipse.core.command.error.CommandExecutionException
import com.bclipse.core.command.context.CommandContext
import com.bclipse.core.command.context.PlayerCommandContext
import com.bclipse.core.command.error.CommandExecutionErrorReason.IS_NOT_PLAYER
import org.bukkit.entity.Player

fun interface PlayerExecuteStrategy: ExecuteStrategy {
    override fun execute(context: CommandContext): Boolean {
        val sender = context.sender //for type inference

        if(sender !is Player)
            throw CommandExecutionException(
                context,
                reason = IS_NOT_PLAYER,
                debugMessage = "sender isn't Player",
            )

        val newContext = PlayerCommandContext(
            player = sender,
            command = context.command,
            label = context.label,
            args = context.args,
        )
        return execute(newContext)
    }

    fun execute(context: PlayerCommandContext): Boolean
}