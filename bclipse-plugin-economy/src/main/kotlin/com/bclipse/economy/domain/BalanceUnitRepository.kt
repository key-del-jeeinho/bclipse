package com.bclipse.economy.domain

import java.util.UUID

interface BalanceUnitRepository {
    fun findById(unitId: UUID): BalanceUnit?
    fun findAllByIds(unitIds: List<UUID>): List<BalanceUnit>
    fun findByName(name: String): BalanceUnit?
    fun findAll(): List<BalanceUnit>
}