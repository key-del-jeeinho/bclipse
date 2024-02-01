package com.bclipse.core.command

import com.bclipse.core.command.context.CommandContext

abstract class ChildCommand(
    children: List<ChildCommand>,
): BclipseCommand(children) {
    abstract fun needExecute(context: CommandContext): Boolean
}
