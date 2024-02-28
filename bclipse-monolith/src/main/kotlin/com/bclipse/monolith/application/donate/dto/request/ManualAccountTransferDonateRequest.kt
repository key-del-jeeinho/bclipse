package com.bclipse.monolith.application.donate.dto.request

import com.bclipse.monolith.application.donate.dto.DonateManualAccountTransferDto

data class ManualAccountTransferDonateRequest(
    val applicationId: String,
    val amount: Int,
    val depositorName: String,
) {
    fun toDto(userId: String): DonateManualAccountTransferDto =
        DonateManualAccountTransferDto(
            applicationId = applicationId,
            donorId = userId,
            amount = amount,
            depositorName = depositorName,
        )
}
