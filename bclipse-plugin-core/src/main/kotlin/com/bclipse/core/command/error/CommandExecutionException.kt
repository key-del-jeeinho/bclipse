package com.bclipse.core.command.error

import com.bclipse.core.command.context.CommandContext

data class CommandExecutionException(
    val context: CommandContext,
    val reason: CommandExecutionErrorReason,
    val debugMessage: String,
): RuntimeException(debugMessage)