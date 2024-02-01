package com.bclipse.core.command.strategy

import com.bclipse.core.command.context.CommandContext

interface ExecuteStrategy {
    fun execute(context: CommandContext): Boolean
}