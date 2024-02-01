package com.bclipse.economy.application

import KoinExtension.delegate
import com.bclipse.economy.domain.Balance
import com.bclipse.economy.domain.BalanceRepository
import com.bclipse.economy.domain.BalanceUnit
import com.bclipse.economy.domain.BalanceUnitRepository
import java.util.UUID

class BalanceQueryService {
    private val balanceRepository: BalanceRepository by delegate()
    private val balanceUnitRepository: BalanceUnitRepository by delegate()

    fun queryMyBalance(userId: UUID): List<Balance> {
        return balanceRepository.queryAllBalanceByUserId(userId)
    }
    fun queryMyBalanceWithUnit(userId: UUID, unitId: UUID): Balance {
        return balanceRepository.queryMyBalanceWithUnit(userId, unitId)
    }

    fun findAllUnitByIds(unitIds: List<UUID>): List<BalanceUnit> {
        return balanceUnitRepository.findAllByIds(unitIds)
    }

    fun queryBalanceUnitByName(name: String): BalanceUnit? {
        return balanceUnitRepository.findByName(name)
    }

    fun queryAllBalanceUnit(): List<BalanceUnit> {
        return balanceUnitRepository.findAll()
    }
}