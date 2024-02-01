package com.bclipse.economy.domain

import java.util.UUID

data class Balance(
    val userId: UUID,
    val unitId: UUID,
    val amount: Long,
)