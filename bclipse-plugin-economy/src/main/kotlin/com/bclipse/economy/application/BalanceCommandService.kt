package com.bclipse.economy.application

import KoinExtension.delegate
import com.bclipse.core.standard.Deferred
import com.bclipse.core.standard.deferred
import com.bclipse.economy.application.dto.ChangeBalanceDto
import com.bclipse.economy.domain.Balance
import com.bclipse.economy.domain.BalanceRepository

class BalanceCommandService {
    private val repository: BalanceRepository by delegate()
    private val balanceQueryService: BalanceQueryService by delegate()

    fun addBalance(dto: ChangeBalanceDto): Deferred<Balance> {
        require(dto.amount > 0) { "증액할 금액은 항상 절대값으로 입력해주세요" }
        repository.create(dto.userId, dto.unitId, dto.amount)
        return deferred {
            balanceQueryService.queryMyBalanceWithUnit(
                dto.userId,
                dto.unitId
            )
        }
    }

    fun minusBalance(dto: ChangeBalanceDto): Deferred<Balance> {
        require(dto.amount > 0) { "차감할 금액은 항상 절대값으로 입력해주세요" }
        repository.create(dto.userId, dto.unitId, dto.amount)
        return deferred {
            balanceQueryService.queryMyBalanceWithUnit(
                dto.userId,
                dto.unitId
            )
        }
    }
}