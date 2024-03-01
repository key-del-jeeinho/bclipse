package com.bclipse.monolith.application.user.dto

import com.bclipse.monolith.application.user.dto.query.UserQueryResultDto

data class UserIdDto(val id: String): UserQueryResultDto {
    companion object {
        fun String.toIdDto(): UserIdDto = UserIdDto(id = this)
    }

    override fun getQueryResultId(): String = id
}