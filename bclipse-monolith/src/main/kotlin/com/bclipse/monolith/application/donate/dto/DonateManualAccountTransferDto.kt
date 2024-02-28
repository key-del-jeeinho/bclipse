package com.bclipse.monolith.application.donate.dto

data class DonateManualAccountTransferDto(
    val applicationId: String,
    val donorId: String,
    val amount: Int,
    val depositorName: String,
)