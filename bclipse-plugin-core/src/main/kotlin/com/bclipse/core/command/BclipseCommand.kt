package com.bclipse.core.command

import com.bclipse.core.command.context.CommandContext
import com.bclipse.core.command.strategy.ExecuteStrategy
import com.bclipse.core.standard.ListUtil.removeFirst
import org.bukkit.Bukkit

abstract class BclipseCommand(
    private val children: List<ChildCommand>
): ExecuteStrategy {
    fun delegateOrExecute(context: CommandContext): Boolean {
        val targets = children.filter { child -> child.needExecute(context) }

        logIfMultipleTargetDetected(targets)

        return if(targets.isEmpty()) execute(context)
        else {
            val nextArgList = context.args.removeFirst()
            val childContext = context.copy(args = nextArgList)

            targets.any { target -> target.execute(childContext) }
        }
    }

    abstract override fun execute(context: CommandContext): Boolean

    private fun logIfMultipleTargetDetected(
        targets: List<ChildCommand>
    ) {
        if(targets.size > 1)
            Bukkit.getLogger().warning(
                "multiple child commands needExecute() return true."
            )
    }
}