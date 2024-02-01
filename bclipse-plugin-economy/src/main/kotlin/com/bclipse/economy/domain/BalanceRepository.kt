package com.bclipse.economy.domain

import java.util.UUID

interface BalanceRepository {
    fun create(
        userId: UUID,
        unitId: UUID,
        amount: Long
    ): BalanceTransaction
    fun queryAllBalanceByUserId(userId: UUID): List<Balance>
    fun queryMyBalanceWithUnit(userId: UUID, unitId: UUID): Balance
}