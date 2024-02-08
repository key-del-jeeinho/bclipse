package com.bclipse.application.user.dto

import java.time.ZonedDateTime

class UserProfileDto (
    val id: String,
    val name : String,
    val createdAt: ZonedDateTime,
)
