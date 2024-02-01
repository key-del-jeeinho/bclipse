package com.bclipse.economy.adapter.outbound

import com.bclipse.economy.domain.Balance
import com.bclipse.economy.domain.BalanceRepository
import com.bclipse.economy.domain.BalanceTransaction
import java.time.ZonedDateTime
import java.util.UUID

class BalanceInMemoryAdapter: BalanceRepository {
    companion object {
        private val source: MutableMap<UUID, BalanceTransaction> = mutableMapOf()
    }

    override fun create(userId: UUID, unitId: UUID, amount: Long): BalanceTransaction {
        val transactionId = UUID.randomUUID()
        val transaction = BalanceTransaction(
            transactionId = transactionId,
            userId = userId,
            unitId = unitId,
            amount = amount,
            createdAt = ZonedDateTime.now()
        )
        source[transactionId] = transaction
        return transaction
    }

    override fun queryAllBalanceByUserId(userId: UUID): List<Balance> {
        val transactionsByUnits = source
            .values
            .filter { transaction -> transaction.userId == userId }
            .groupBy { it.unitId }

        return transactionsByUnits.map { (unitId, transaction) ->
            val amount = transaction.sumOf(BalanceTransaction::amount)

            return@map Balance(
                userId = userId,
                unitId = unitId,
                amount = amount,
            )
        }
    }

    override fun queryMyBalanceWithUnit(userId: UUID, unitId: UUID): Balance {
        val transactions = source
            .values
            .filter { transaction -> transaction.userId == userId }
            .filter { transaction -> transaction.unitId == unitId }
        val amount = transactions.sumOf(BalanceTransaction::amount)

        return Balance(
            userId = userId,
            unitId = unitId,
            amount = amount,
        )
    }
}