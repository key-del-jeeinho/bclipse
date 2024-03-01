package com.bclipse.monolith.application.user.dto

import java.time.ZonedDateTime

data class UserProfileDto (
    val id: String,
    val name : String,
    val createdAt: ZonedDateTime,
): UserQueryResultDto {
    override fun getQueryResultId(): String = id
}
