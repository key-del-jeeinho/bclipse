package com.bclipse.economy.adapter.inbound.command

import KoinExtension.delegate
import com.bclipse.economy.application.BalanceQueryService
import com.bclipse.economy.domain.Balance
import com.bclipse.economy.domain.BalanceDomainService
import com.bclipse.economy.domain.BalanceUnit
import org.bukkit.command.CommandSender

class BalancePresenter {
    private val balanceQueryService: BalanceQueryService by delegate()

    fun presentError(sender: CommandSender, throwable: Throwable) {
        if(throwable !is IllegalArgumentException)
            throw throwable
        sender.sendMessage(throwable.message)
    }

    fun presentMyBalance(sender: CommandSender, balances: List<Balance>) {
        val units = balanceQueryService.findAllUnitByIds(
            balances.map(Balance::unitId)
        )

        val unitsByBalances = BalanceDomainService.aggregateToUnitsByBalances(balances, units)

        val displayBalance = (
            if(unitsByBalances.isEmpty()) "you don't have any balance"
            else unitsByBalances
                .map { (unit, balance) -> "${unit.name} : ${balance.amount}" }
                .joinToString("\n", prefix = "Your balance is...\n")
            )

        sender.sendMessage(displayBalance)
    }

    fun presentBalanceConfig(sender: CommandSender, units: List<BalanceUnit>) {
        val displayBalance =
            units.joinToString("\n", prefix = "Current Balance Config") {
                unit -> "${unit.name} (${unit.unitId})"
            }

        sender.sendMessage(displayBalance)
    }
}