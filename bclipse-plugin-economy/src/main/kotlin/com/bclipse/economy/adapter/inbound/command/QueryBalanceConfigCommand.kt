package com.bclipse.economy.adapter.inbound.command

import KoinExtension.delegate
import com.bclipse.core.command.AliasPlayerCommand
import com.bclipse.core.command.context.PlayerCommandContext
import com.bclipse.economy.application.BalanceQueryService

class QueryBalanceConfigCommand(
    vararg aliases: String,
): AliasPlayerCommand(aliases = aliases) {
    private val balanceQueryService: BalanceQueryService by delegate()
    private val balancePresenter: BalancePresenter by delegate()

    override fun execute(context: PlayerCommandContext): Boolean {
        val units = balanceQueryService.queryAllBalanceUnit()
        balancePresenter.presentBalanceConfig(context.player, units)
        return true
    }
}