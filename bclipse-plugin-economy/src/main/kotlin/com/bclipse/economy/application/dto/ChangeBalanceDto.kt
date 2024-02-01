package com.bclipse.economy.application.dto

import java.util.UUID

data class ChangeBalanceDto(
    val userId: UUID,
    val unitId: UUID,
    val amount: Long,
)