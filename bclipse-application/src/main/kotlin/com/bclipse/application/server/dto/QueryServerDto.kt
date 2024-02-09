package com.bclipse.application.server.dto

import com.bclipse.application.common.entity.Base64UUID

class QueryServerDto(
    val serverId: Base64UUID,
    val userId: String,
)