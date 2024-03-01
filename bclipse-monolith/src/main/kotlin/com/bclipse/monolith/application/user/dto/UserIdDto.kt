package com.bclipse.monolith.application.user.dto

data class UserIdDto(val id: String): UserQueryResultDto {
    companion object {
        fun String.toIdDto(): UserIdDto = UserIdDto(id = this)
    }

    override fun getQueryResultId(): String = id
}