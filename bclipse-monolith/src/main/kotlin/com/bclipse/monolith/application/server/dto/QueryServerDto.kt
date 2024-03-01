package com.bclipse.monolith.application.server.dto

import com.bclipse.lib.common.entity.Base64UUID

class QueryServerDto(
    val serverId: Base64UUID,
    val userId: String,
)