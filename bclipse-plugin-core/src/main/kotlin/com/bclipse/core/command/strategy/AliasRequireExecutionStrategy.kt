package com.bclipse.core.command.strategy

import com.bclipse.core.command.context.CommandContext

class AliasRequireExecutionStrategy(private vararg val aliases: String): RequireExecutionStrategy {
    override fun isExecutable(context: CommandContext): Boolean =
        context.args.firstOrNull()
            ?.let(aliases::contains)
            ?: false
}