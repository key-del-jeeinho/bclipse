package com.bclipse.economy.adapter.inbound.command

import KoinExtension.delegate
import com.bclipse.core.command.AliasPlayerCommand
import com.bclipse.core.command.context.PlayerCommandContext
import com.bclipse.economy.application.BalanceQueryService

class QueryMyBalanceCommand(
    vararg aliases: String,
): AliasPlayerCommand(aliases = aliases) {
    private val balanceQueryService: BalanceQueryService by delegate()
    private val balancePresenter: BalancePresenter by delegate()

    override fun execute(context: PlayerCommandContext): Boolean {
        val playerId = context.player.uniqueId
        val balances = balanceQueryService.queryMyBalance(playerId)
        balancePresenter.presentMyBalance(context.player, balances)
        return true
    }
}