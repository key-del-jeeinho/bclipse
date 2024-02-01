package com.bclipse.core.command

import com.bclipse.core.command.context.PlayerCommandContext
import com.bclipse.core.command.strategy.AliasRequireExecutionStrategy
import com.bclipse.core.command.strategy.ExecuteStrategy
import com.bclipse.core.command.strategy.PlayerExecuteStrategy
import com.bclipse.core.command.strategy.RequireExecutionStrategy

abstract class AliasPlayerCommand(
    children: List<ChildCommand> = emptyList(),
    vararg aliases: String,
): StrategyBaseChildCommand(children) {
    override val requireExecutionStrategy: RequireExecutionStrategy =
        AliasRequireExecutionStrategy(*aliases)

    override val executeStrategy: ExecuteStrategy =
        PlayerExecuteStrategy(::execute)

    abstract fun execute(context: PlayerCommandContext): Boolean
}