package com.bclipse.monolith.application.donate.entity.document

import com.bclipse.monolith.common.entity.Base64UUID
import nonapi.io.github.classgraph.json.Id
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.index.Indexed

class ManualAccountTransferDonateDocument(
    @Id val id: ObjectId,
    @Indexed(unique = true)
    val donateId: Base64UUID,
    val depositorName: String,
)