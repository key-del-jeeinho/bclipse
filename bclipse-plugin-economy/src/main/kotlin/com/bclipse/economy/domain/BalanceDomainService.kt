package com.bclipse.economy.domain

object BalanceDomainService {
    fun aggregateToUnitsByBalances(
        balances: List<Balance>,
        units: List<BalanceUnit>,
    ): Map<BalanceUnit, Balance> {
        val unitIdsByBalances = balances.associateBy(Balance::unitId)
        return units.mapNotNull { unit ->
            val balanceOrNull = unitIdsByBalances[unit.unitId]
            balanceOrNull?.let { balance -> unit to balance }
        }.toMap()
    }
}