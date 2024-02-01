package com.bclipse.core.command.strategy

import com.bclipse.core.command.context.CommandContext

fun interface RequireExecutionStrategy {
    fun isExecutable(context: CommandContext): Boolean
}