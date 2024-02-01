package com.bclipse.economy.adapter.inbound.command

import KoinExtension.delegate
import com.bclipse.core.command.AliasPlayerCommand
import com.bclipse.core.command.context.PlayerCommandContext
import com.bclipse.economy.application.BalanceCommandService
import com.bclipse.economy.application.BalanceQueryService
import com.bclipse.economy.application.dto.ChangeBalanceDto

class AddBalanceCommand(
    vararg aliases: String
): AliasPlayerCommand(aliases = aliases) {
    private val balanceQueryService: BalanceQueryService by delegate()
    private val balanceCommandService: BalanceCommandService by delegate()
    private val balancePresenter: BalancePresenter by delegate()

    override fun execute(context: PlayerCommandContext): Boolean {
        val dto = runCatching {
            val (unitName, amountStr) = context.args

            val unit = balanceQueryService.queryBalanceUnitByName(unitName)
                ?: throw IllegalArgumentException("unit 이름이 잘못되었습니다. ($unitName)")
            val amount = amountStr.toLongOrNull()
                ?: throw IllegalArgumentException("amount는 정수로 입력해주세요. ($amountStr)")

            ChangeBalanceDto(
                userId = context.player.uniqueId,
                unitId = unit.unitId,
                amount = amount,
            )
        }.getOrElse { throwable ->
            balancePresenter.presentError(context.player, throwable)
            return true
        }

        val balances = balanceCommandService.addBalance(dto).await()
            .let { balanceQueryService.queryMyBalance(it.userId) }

        balancePresenter.presentMyBalance(context.player, balances)

        return true
    }
}