package com.bclipse.economy.domain

import java.time.ZonedDateTime
import java.util.UUID

data class BalanceTransaction(
    val transactionId: UUID,
    val unitId: UUID,
    val userId: UUID,
    val amount: Long,
    val createdAt: ZonedDateTime,
)