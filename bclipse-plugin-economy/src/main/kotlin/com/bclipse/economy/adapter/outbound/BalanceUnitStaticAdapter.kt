package com.bclipse.economy.adapter.outbound

import com.bclipse.economy.domain.BalanceUnit
import com.bclipse.economy.domain.BalanceUnitRepository
import java.util.UUID

class BalanceUnitStaticAdapter: BalanceUnitRepository {
    companion object {
        private val source: Map<UUID, BalanceUnit> =
            mapOf(
                toSource("c9c92622-f3cb-443d-809a-298c83cb4d5a", "원"),
                toSource("0de82ff9-bd11-4fde-9259-558acad533c7", "캐시"),
                toSource("e8620267-1efe-430e-aec0-bb1c4fe070c7", "마일리지"),
                toSource("cc62e2e5-00ef-4d5d-9fe6-4bbe9c8df81d", "포인트"),
            )

        private fun toSource(
            uuidString: String,
            name: String
        ): Pair<UUID, BalanceUnit> {
            val uuid = UUID.fromString(uuidString)
            return uuid to BalanceUnit(uuid, name)
        }
    }

    override fun findById(unitId: UUID): BalanceUnit? =
        source[unitId]

    override fun findAllByIds(unitIds: List<UUID>): List<BalanceUnit> =
        unitIds.mapNotNull(source::get)

    override fun findByName(name: String): BalanceUnit? =
        source.values.firstOrNull { unit -> unit.name == name }

    override fun findAll(): List<BalanceUnit> = source.values.toList()
}