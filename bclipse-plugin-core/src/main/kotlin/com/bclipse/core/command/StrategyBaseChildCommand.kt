package com.bclipse.core.command

import com.bclipse.core.command.context.CommandContext
import com.bclipse.core.command.strategy.ExecuteStrategy
import com.bclipse.core.command.strategy.RequireExecutionStrategy

abstract class StrategyBaseChildCommand(
    children: List<ChildCommand>,
): ChildCommand(children) {
    abstract val requireExecutionStrategy: RequireExecutionStrategy
    abstract val executeStrategy: ExecuteStrategy

    final override fun needExecute(context: CommandContext): Boolean =
        requireExecutionStrategy.isExecutable(context)

    final override fun execute(context: CommandContext): Boolean =
        executeStrategy.execute(context)
}