package com.bclipse.core.command.strategy

import com.bclipse.core.command.context.CommandContext

class CompoundRequireExecutionStrategy(
    private val strict: Boolean,
    private vararg val strategies: RequireExecutionStrategy
): RequireExecutionStrategy {
    override fun isExecutable(context: CommandContext): Boolean {
        val isExecutable = { strategy: RequireExecutionStrategy -> strategy.isExecutable(context) }

        return (
            if(strict) strategies.all(isExecutable)
            else strategies.any(isExecutable)
        )
    }
}